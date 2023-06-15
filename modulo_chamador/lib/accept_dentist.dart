import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:flutter/material.dart';
import 'package:geolocator/geolocator.dart';
import 'package:modulo_chamador/service_info.dart';

class AcceptDentist extends StatefulWidget {
  final String docId;
  final GeoPoint location;

  const AcceptDentist({super.key, required this.docId, required this.location});

  @override
  State<AcceptDentist> createState() => _AcceptDentistState();
}

class _AcceptDentistState extends State<AcceptDentist> {
  late Stream<QuerySnapshot> stream;
  final FirebaseFirestore _firestore = FirebaseFirestore.instance;

  @override
  void initState() {
    super.initState();
    stream = FirebaseFirestore.instance
        .collection('Atendimentos')
        .where("emergency", isEqualTo: widget.docId)
        .where("status", isEqualTo: "1")
        .snapshots();
  }

  Future<DocumentSnapshot> getDentist(String dentistUid) async {
    QuerySnapshot dentistQuery = await FirebaseFirestore.instance
        .collection('Dentista')
        .where("uid", isEqualTo: dentistUid)
        .get();

    if (dentistQuery.docs.isNotEmpty) {
      return dentistQuery.docs.first;
    }

    throw Exception('Dentist not found');
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          backgroundColor: const Color(0xFFF0FAF9),
          centerTitle: true,
          title: const Text(
            'Solicitar Profissional',
            style: TextStyle(
              color: Color(0xFF292929),
              fontFamily: 'Poppins',
            ),
          ),
        ),
        backgroundColor: const Color(0xFFF0FAF9),
        body: StreamBuilder<QuerySnapshot>(
          stream: stream,
          builder:
              (BuildContext context, AsyncSnapshot<QuerySnapshot> snapshot) {
            if (snapshot.hasError) {
              return const Text('Something went wrong');
            }

            if (snapshot.connectionState == ConnectionState.waiting) {
              return const Center(child: CircularProgressIndicator());
            }

            return ListView.builder(
              itemCount: snapshot.data!.docs.length,
              itemBuilder: (context, index) {
                var doc = snapshot.data!.docs[index];
                return FutureBuilder<DocumentSnapshot>(
                  future: getDentist(doc['dentist']),
                  builder: (BuildContext context,
                      AsyncSnapshot<DocumentSnapshot> dentistSnapshot) {
                    if (dentistSnapshot.hasError) {
                      return const Text('Something went wrong');
                    }
                    if (dentistSnapshot.connectionState ==
                        ConnectionState.waiting) {
                      return const Center(child: CircularProgressIndicator());
                    }
                    var dentistDoc = dentistSnapshot.data!;
                    GeoPoint dentistLocation = doc['location'];
                    double distanceInMeters = Geolocator.distanceBetween(
                        widget.location.latitude,
                        widget.location.longitude,
                        dentistLocation.latitude,
                        dentistLocation.longitude) /
                        1000;
                    String distance = distanceInMeters.toStringAsFixed(2);
                    return Center(
                      child: Material(
                        elevation: 8,
                        shadowColor: const Color(0xFFC0ECE8),
                        borderRadius: BorderRadius.circular(35),
                        child: Container(
                          width: 318,
                          height: 125,
                          padding: const EdgeInsets.all(25),
                          decoration: BoxDecoration(
                            color: Colors.white,
                            borderRadius: BorderRadius.circular(35),
                          ),
                          child: Row(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              Expanded(
                                child: Column(
                                  crossAxisAlignment: CrossAxisAlignment.center,
                                  children: [
                                    Text(
                                      dentistDoc['nome'],
                                      style: const TextStyle(fontSize: 18),
                                    ),
                                    const SizedBox(height: 10),
                                    Row(
                                      children: [
                                        Container(
                                          padding: const EdgeInsets.all(10),
                                          decoration: BoxDecoration(
                                            borderRadius:
                                            BorderRadius.circular(15),
                                            color: const Color(0xffb5ef55),
                                          ),
                                          child: const Row(
                                            mainAxisSize: MainAxisSize.min,
                                            children: [
                                              Icon(
                                                Icons.star,
                                                size: 20,
                                                color: Color(0xffe7ffbf),
                                              ),
                                              SizedBox(width: 5),
                                              Text(
                                                '4.5',
                                                style: TextStyle(
                                                  fontSize: 16,
                                                  color: Colors.white,
                                                ),
                                              ),
                                            ],
                                          ),
                                        ),
                                        const SizedBox(width: 10),
                                        Row(
                                          mainAxisSize: MainAxisSize.min,
                                          children: [
                                            const Icon(
                                              Icons.location_on,
                                              size: 20,
                                            ),
                                            const SizedBox(width: 5),
                                            Text(
                                              "$distance km",
                                              style:
                                              const TextStyle(fontSize: 16),
                                            ),
                                          ],
                                        ),
                                      ],
                                    ),
                                  ],
                                ),
                              ),
                              const SizedBox(width: 20),
                              Container(
                                width: 50,
                                height: 50,
                                decoration: const BoxDecoration(
                                  shape: BoxShape.circle,
                                  color: Colors.blue,
                                ),
                                child: IconButton(
                                  onPressed: () => _onButtonPressed(doc['id'], dentistLocation, dentistDoc['telefone']),
                                  icon: const Icon(
                                    Icons.check,
                                    color: Colors.white,
                                  ),
                                ),
                              ),
                            ],
                          ),
                        ),
                      ),
                    );
                  },
                );
              },
            );
          },
        ),
      ),
    );
  }

  void _onButtonPressed(String id, GeoPoint dentistLocation, String phone) async {
    // Perform Firestore update
    await _acceptDentist(id);
    if (mounted) {
      Navigator.of(context).push(
        MaterialPageRoute(builder: (context) =>
            MapSample(
              callerLatitude: widget.location.latitude,
              callerLongitude: widget.location.longitude,
              dentistLatitude: dentistLocation.latitude,
              dentistLongitude: dentistLocation.longitude,
              phone: phone,
            )),
      );
    }
  }

  Future<void> _acceptDentist(String id) async {
    await _firestore.collection("Atendimentos").doc(id).set({
      'status': '2',
    }, SetOptions(merge: true));
  }
}