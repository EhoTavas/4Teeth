import 'package:flutter/material.dart';


class CallEmergencyWidget extends StatefulWidget {
  const CallEmergencyWidget({Key? key}) : super(key: key);

  @override
  _CallEmergencyWidgetState createState() => _CallEmergencyWidgetState();
}

class _CallEmergencyWidgetState extends State<CallEmergencyWidget> {
  @override
  Widget build(BuildContext context) {

    return Scaffold(
        appBar: AppBar(
          title: const Text('4Teeth'),
          centerTitle: true,
        ),
      body: Container(
        width: 360,
        height: 800,
        decoration: const BoxDecoration(
          color : Color.fromRGBO(240, 250, 249, 1),
        ),
        child: Stack(
            children: <Widget>[
              const Positioned(
                  top: 18,
                  left: 146,
                  child: Text('4Teeth', textAlign: TextAlign.center, style: TextStyle(
                      color: Color.fromRGBO(41, 41, 41, 1),
                      fontFamily: 'Poppins',
                      fontSize: 20,
                      letterSpacing: 0,
                      fontWeight: FontWeight.normal,
                      height: 1
                  ),)
              ),Positioned(
                  top: 246,
                  left: 35,
                  child: SizedBox(
                      width: 290,
                      height: 242,

                      child: Stack(
                          children: <Widget>[
                            Positioned(
                                top: 0,
                                left: 0,
                                child: Container(
                                    width: 290,
                                    height: 242,
                                    decoration: const BoxDecoration(
                                      borderRadius : BorderRadius.only(
                                        topLeft: Radius.circular(55),
                                        topRight: Radius.circular(55),
                                        bottomLeft: Radius.circular(55),
                                        bottomRight: Radius.circular(55),
                                      ),
                                      boxShadow : [BoxShadow(
                                          color: Color.fromRGBO(0, 0, 0, 0.25),
                                          offset: Offset(0,4),
                                          blurRadius: 4
                                      )],
                                      color : Color.fromRGBO(255, 255, 255, 1),
                                    )
                                )
                            ),const Positioned(
                                top: 32,
                                left: 37,
                                child: Text('Precisando de ajuda?', textAlign: TextAlign.center, style: TextStyle(
                                    color: Color.fromRGBO(41, 41, 41, 1),
                                    fontFamily: 'Poppins',
                                    fontSize: 20,
                                    letterSpacing: 0,
                                    fontWeight: FontWeight.normal,
                                    height: 1
                                ),)
                            ),Positioned(
                                top: 92,
                                left: 21,
                                child: Container(),
                            ),
                          ]
                      )
                  )
              ),Positioned(
                  top: 418,
                  left: 1465,
                  child: Container(),
              ),
            ]
        )
    ));
  }
}
