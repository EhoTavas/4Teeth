import 'dart:async';
import 'dart:io';
import 'package:http/http.dart' as http;
import 'dart:convert' as convert;
import 'package:google_maps_flutter/google_maps_flutter.dart';
import 'package:url_launcher/url_launcher.dart';

import 'package:flutter/material.dart';

class MapSample extends StatefulWidget {
  final double callerLatitude;
  final double callerLongitude;
  final double dentistLatitude;
  final double dentistLongitude;
  final String phone;

  const MapSample({
    Key? key,
    required this.callerLatitude,
    required this.callerLongitude,
    required this.dentistLatitude,
    required this.dentistLongitude,
    required this.phone,
  }) : super(key: key);

  @override
  State<MapSample> createState() => MapSampleState();
}

class MapSampleState extends State<MapSample> {
  final Set<Polyline> _polylines = <Polyline>{};
  List<LatLng> polylineCoordinates = [];
  final Completer<GoogleMapController> _controller = Completer<GoogleMapController>();

  final Set<Marker> _markers = {};
  Future<void> _createPolylines(double startLatitude, double startLongitude, double destinationLatitude, double destinationLongitude) async {
    String url = "";
    if (Platform.isAndroid) {
      url = "https://maps.googleapis.com/maps/api/directions/json?"
          "origin=$startLatitude,$startLongitude&"
          "destination=$destinationLatitude,$destinationLongitude&"
          "key=AIzaSyDofvsET-lnziPi5QAfIhBaWD7wI3uV6m8";
    } else if (Platform.isIOS) {
      url = "https://maps.googleapis.com/maps/api/directions/json?"
          "origin=$startLatitude,$startLongitude&"
          "destination=$destinationLatitude,$destinationLongitude&"
          "key=AIzaSyDofvsET-lnziPi5QAfIhBaWD7wI3uV6m8";
    }
    http.Response response = await http.get(Uri.parse(url));
    Map values = convert.jsonDecode(response.body);
    _createRoute(values["routes"][0]["overview_polyline"]["points"]);
  }

  void _createRoute(String encodedPoly) {
    setState(() {
      _polylines.add(Polyline(
        polylineId: const PolylineId("route1"),
        visible: true,
        points: _convertToLatLng(_decodePoly(encodedPoly)),
        color: Colors.green.shade300,
        width: 1,
      ));
    });
  }

  List<LatLng> _convertToLatLng(List points) {
    List<LatLng> result = <LatLng>[];
    for (int i = 0; i < points.length; i++) {
      if (i % 2 != 0) {
        result.add(LatLng(points[i - 1], points[i]));
      }
    }
    return result;
  }

  List _decodePoly(String poly) {
    var list = poly.codeUnits;
    List<double> lList = [];
    int index = 0;
    int len = poly.length;
    int c = 0;
    do {
      var shift = 0;
      int result = 0;
      do {
        c = list[index] - 63;
        result |= (c & 0x1F) << (shift * 5);
        index++;
        shift++;
      } while (c >= 32);
      if (result & 1 == 1) {
        result = ~result;
      }
      var result1 = (result >> 1) * 0.00001;
      lList.add(result1);
    } while (index < len);

    for (var i = 2; i < lList.length; i++) {
      lList[i] += lList[i - 2];
    }

    return lList;
  }
  @override
  void initState() {
    super.initState();
    setupPolylines();
  }

  Future<void> setupPolylines() async {
    _markers.add(
      Marker(
        markerId: const MarkerId('dentist'),
        position: LatLng(widget.dentistLatitude, widget.dentistLongitude),
      ),
    );
    _markers.add(
      Marker(
        markerId: const MarkerId('caller'),
        position: LatLng(widget.callerLatitude, widget.callerLongitude),
      ),
    );
    await _createPolylines(widget.callerLatitude, widget.callerLongitude, widget.dentistLatitude, widget.dentistLongitude);
    setState(() {});
  }

  @override
  Widget build(BuildContext context) {
    CameraPosition kGooglePlex = CameraPosition(
      target: LatLng(widget.dentistLatitude, widget.dentistLongitude),
      zoom: 15.000,
    );
    return Scaffold(
      body: Stack(
        children: [
          GoogleMap(
            mapType: MapType.normal,
            initialCameraPosition: kGooglePlex,
            markers: _markers,
            polylines: _polylines,
            onMapCreated: (GoogleMapController controller) {
              _controller.complete(controller);
            },
          ),
          // Positioned(
          //   bottom: 10,
          //   left: 10,
          //   right: 10,
          //   child: Container(
          //     padding: const EdgeInsets.symmetric(horizontal: 10.0, vertical: 10.0),
          //     decoration: BoxDecoration(
          //       color: Colors.white,
          //       borderRadius: BorderRadius.circular(50.0),
          //       boxShadow: [BoxShadow(
          //         color: Colors.black.withOpacity(0.16),
          //         offset: const Offset(0, 3),
          //         blurRadius: 6,
          //       )],
          //     ),
          //     child: Row(
          //       mainAxisAlignment: MainAxisAlignment.spaceBetween,
          //       children: <Widget>[
          //         Text(
          //           widget.phone, // Substitua com o número de telefone do dentista
          //           style: const TextStyle(
          //             fontSize: 16,
          //             color: Colors.black,
          //           ),
          //         ),
          //         Container(
          //           width: 50,
          //           height: 50,
          //           decoration: const BoxDecoration(
          //             shape: BoxShape.circle,
          //             color: Colors.lightBlue,
          //           ),
          //           child: IconButton(
          //             onPressed: () {
          //               final strippedNumber = widget.phone.replaceAll(RegExp(r'[^0-9+]'), '');
          //               _launchURL("tel:$strippedNumber");
          //             }, // Substitua com o número de telefone do dentista
          //             icon: const Icon(
          //               Icons.phone,
          //               color: Colors.white,
          //             ),
          //           ),
          //         ),
          //       ],
          //     ),
          //   ),
          // ),
        ],
      ),
    );
  }

  void _launchURL(String url) async =>
      await canLaunch(url) ? await launch(url) : throw 'Could not launch $url';

}