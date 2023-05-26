import 'dart:ffi';
import 'dart:async';
import 'dart:io';

import 'package:firebase_database/firebase_database.dart';
import 'package:firebase_storage/firebase_storage.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:flutter/material.dart';
import 'package:cloud_firestore/cloud_firestore.dart';


class StartEmergencyWidget extends StatefulWidget {
  @override
  _StartEmergencyWidgetState createState() => _StartEmergencyWidgetState();
}

class _CallEmergencyWidgetState extends State<TelainicialWidget> {
  @override
  Widget build(BuildContext context) {

    return Container(
        width: 360,
        height: 800,
        decoration: BoxDecoration(
          color : Color.fromRGBO(240, 250, 249, 1),
        ),
        child: Stack(
            children: <Widget>[
              Positioned(
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
                                    decoration: BoxDecoration(
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
                            ),Positioned(
                                top: 32,
                                left: 37,
                                child: Text('Precisando de ajuda?', textAlign: TextAlign.center, style: TextStyle(
                                    color: Color.fromRGBO(41, 41, 41, 1),
                                    fontFamily: 'Poppins',
                                    fontSize: 20,
                                    letterSpacing: 0
                                    fontWeight: FontWeight.normal,
                                    height: 1
                                ),)
                            ),Positioned(
                                top: 92,
                                left: 21,
                                child: null
                            ),
                          ]
                      )
                  )
              ),Positioned(
                  top: 418,
                  left: 1465,
                  child: null
              ),
            ]
        )
    );
  }
}
