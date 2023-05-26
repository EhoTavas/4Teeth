import 'dart:ffi';
import 'dart:async';
import 'dart:io';

import 'package:flutter/material.dart';
import 'package:path/path.dart' as Path;

import 'call_emergency.dart';

class DisplayPicture extends StatefulWidget {
  final String imagePath;

  const DisplayPicture({super.key, required this.imagePath});

  @override
  DisplayPictureState createState() => DisplayPictureState();
}

class DisplayPictureState extends State<DisplayPicture> {
  late final NavigatorState _navigator;

  @override
  void initState() {
    super.initState();
    _navigator = Navigator.of(context);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Display the Picture')),
      body: Image.file(File(widget.imagePath)),
      floatingActionButton: FloatingActionButton(
        onPressed: continueEmergency,
        child: const Icon(Icons.navigate_next),
      ),
    );
  }

  void continueEmergency() async {
    await _navigator.push(
      MaterialPageRoute(builder: (context) => const CallEmergency()),
    );
  }
}
