import 'dart:ffi';
import 'dart:async';
import 'dart:io';

import 'package:firebase_storage/firebase_storage.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:flutter/material.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:path_provider/path_provider.dart';
import 'firebase_options.dart';
import 'package:camera/camera.dart';
import 'package:gallery_saver/gallery_saver.dart';
import 'package:image_picker/image_picker.dart';
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
