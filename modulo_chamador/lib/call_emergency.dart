import 'dart:ffi';
import 'dart:async';
import 'dart:io';

import 'package:firebase_database/firebase_database.dart';
import 'package:firebase_storage/firebase_storage.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:flutter/material.dart';
import 'package:cloud_firestore/cloud_firestore.dart';

class CallEmergency extends StatefulWidget {
  const CallEmergency({super.key});

  @override
  CallEmergencyState createState() => CallEmergencyState();
}

class CallEmergencyState extends State<CallEmergency> {
  TextEditingController nameController = TextEditingController();
  TextEditingController phoneController = TextEditingController();
  FirebaseFirestore db = FirebaseFirestore.instance;
  late final NavigatorState _navigator;

  @override
  void dispose() {
    nameController.dispose();
    phoneController.dispose();
    super.dispose();
  }

  @override
  void initState() {
    super.initState();
    _navigator = Navigator.of(context);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Criar Emergência')),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            SizedBox(
              width: 250,
              child: TextField(
                controller: nameController,
                decoration: const InputDecoration(
                  border: OutlineInputBorder(),
                  labelText: 'Nome:',
                ),
              ),
            ),
            SizedBox(
              width: 250,
              child: TextField(
                controller: phoneController,
                keyboardType: TextInputType.phone,
                decoration: const InputDecoration(
                  border: OutlineInputBorder(),
                  labelText: 'Telefone:',
                ),
              ),
            ),
            FloatingActionButton(
              onPressed: _submitEmergency,
            )
          ],
        ),
      ),
    );
  }

  void _submitEmergency() async {
    try {
      // Define os dados que serão enviados
      Map<String, dynamic> data = {
        'nome': 'Mlk fudido',
        'telefone': '+55987654321'
      };

    await db.collection("Emergencias").add(data).then((documentSnapshot) =>
          print("Added Data with ID: ${documentSnapshot.id}"));
    } catch(error) {
      print('deu merda aqui $error');
    }
  }
}
