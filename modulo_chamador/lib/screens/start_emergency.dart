import 'package:uuid/uuid.dart';
import 'package:flutter/material.dart';

var uuid = const Uuid();

class PacientDataPage extends StatefulWidget {
  const PacientDataPage({super.key, required this.title});

  final String title;

  @override
  State<PacientDataPage> createState() => _PacientDataPageState();
}

class _PacientDataPageState extends State<PacientDataPage> {
  String nome = "";
  String telefone = "";

  @override
  Widget build(BuildContext context) {
    // final args = ModalRoute.of(context)!.settings.arguments as ScreenArguments;

    return Scaffold(
      appBar: AppBar(
        title: const Text('Solicitar Socorro'),
        centerTitle: true,
      ),
      body: Container(
          width: 360,
          height: 606,
          decoration: const BoxDecoration(
            borderRadius : BorderRadius.only(
              topLeft: Radius.circular(45),
              topRight: Radius.circular(45),
              bottomLeft: Radius.circular(0),
              bottomRight: Radius.circular(0),
            ),
            color : Color.fromRGBO(228, 255, 254, 1),
          ),
          child: SizedBox(
          width: 275,
          height: 104,
            child: Stack(
                children: <Widget>[
                  const Positioned(
                      top: 0,
                      left: 0,
                      child: Text('Fotografe a região/boca acidentada:', textAlign: TextAlign.center, style: TextStyle(
                          color: Color.fromRGBO(0, 0, 0, 1),
                          fontFamily: 'Poppins',
                          fontSize: 16,
                          letterSpacing: 0,
                          fontWeight: FontWeight.normal,
                          height: 1
                      ),)
                  ),Positioned(
                      top: 56,
                      left: 26,
                      child: Container(
                          width: 59,
                          height: 48,
                          decoration: const BoxDecoration(
                            boxShadow : [BoxShadow(
                                color: Color.fromRGBO(101, 206, 207, 1),
                                offset: Offset(15,15),
                                blurRadius: 50
                            )],
                            image : DecorationImage(
                                image: AssetImage('assets/images/CAM.png'),
                                fit: BoxFit.fitWidth
                            ),
                          )
                      )
                  ),
                ]
            ),
          ),
      )
    );
  }
}
