import 'dart:io';

import 'package:firebase_core/firebase_core.dart';
import 'package:firebase_storage/firebase_storage.dart';
import 'package:flutter/material.dart';
import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:image_picker/image_picker.dart';
import 'package:flutter/services.dart' show rootBundle;
import 'package:modulo_chamador/accept_dentist.dart';
import 'package:path/path.dart' as Path;
import 'package:geolocator/geolocator.dart';
import 'package:permission_handler/permission_handler.dart';
import 'package:fluttertoast/fluttertoast.dart';

import 'display_picture.dart';
import 'firebase_options.dart';

//Tela inicial
Future<void> main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await Firebase.initializeApp(options: DefaultFirebaseOptions.currentPlatform);
  try {
    UserCredential userCredential = await FirebaseAuth.instance.signInAnonymously();

    print('Usuário anônimo logado com sucesso: ${userCredential.user!.uid}');
  } catch (e) {
    print('Falha ao fazer login anônimo: $e');
  }
  runApp(
    MaterialApp(
      debugShowCheckedModeBanner: false,
      theme: ThemeData.dark(),
      home: const MyApp(),
    ),
  );
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  TextEditingController nameController = TextEditingController();
  TextEditingController phoneController = TextEditingController();
  FirebaseFirestore db = FirebaseFirestore.instance;
  late final NavigatorState _navigator;
  XFile? fotoBoca;
  XFile? fotoDocumento;
  XFile? fotoCrianca;

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
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          backgroundColor: const Color(0xFFF0FAF9),
          centerTitle: true,
          title: const Text(
            'Solicitar Socorro',
            style: TextStyle(
              color: Color(0xFF292929),
              fontFamily: 'Poppins',
            ),
          ),
        ),
        body: Container(
          decoration: const BoxDecoration(
            gradient: RadialGradient(
              colors: [
                Color(0xFF24B8B8), // #24B8B8
                Color(0xFFF0FAF9), // #f0faf9
              ],
            ),
          ),
          child: SingleChildScrollView(
            child: Column(
              children: [
                const SizedBox(height: 75.0),
                Container(
                  decoration: const BoxDecoration(
                    color: Color(0xFFA8E9EB),
                    borderRadius: BorderRadius.only(
                      topLeft: Radius.circular(45.0),
                      topRight: Radius.circular(45.0),
                    ),
                  ),
                  padding: const EdgeInsets.all(7.0),
                  child: Column(
                    children: [
                      ContainerWithText(
                        buttonText: 'Tirar Foto',
                        text: 'Fotografe a região/boca acidentada:',
                        onPhotoTaken: (XFile? photo) {
                          setState(() {
                            fotoBoca = photo;
                          });
                        },
                      ),
                      ContainerWithText(
                        buttonText: 'Tirar Foto',
                        text: 'Envie uma foto do documento do responsável:',
                        onPhotoTaken: (XFile? photo) {
                          setState(() {
                            fotoDocumento = photo;
                          });
                        },
                      ),
                      ContainerWithText(
                        buttonText: 'Tirar Foto',
                        text: 'Tire uma foto com a criança:',
                        onPhotoTaken: (XFile? photo) {
                          setState(() {
                            fotoCrianca = photo;
                          });
                        },
                      ),
                      const SizedBox(height: 1.0),
                      Container(
                        padding: const EdgeInsets.all(0.0),
                        margin: const EdgeInsets.all(10.0),
                        child: Column(
                          children: [
                            Container(
                                decoration: BoxDecoration(
                                  color: Colors.white,
                                  borderRadius: BorderRadius.circular(20.0),
                                ),
                                child: Padding(
                                  padding: const EdgeInsets.symmetric(
                                    horizontal: 20.0,
                                    vertical: 1.0,
                                  ),
                                  child: Column(
                                    crossAxisAlignment:
                                        CrossAxisAlignment.start,
                                    children: [
                                      const Text(
                                        'Nome:',
                                        style: TextStyle(
                                          fontSize: 16.0,
                                          fontWeight: FontWeight.bold,
                                        ),
                                      ),
                                      TextField(
                                        controller: nameController,
                                        decoration: const InputDecoration(
                                          border: InputBorder.none,
                                        ),
                                      ),
                                    ],
                                  ),
                                )),
                            const SizedBox(height: 10.0),
                            Container(
                              decoration: BoxDecoration(
                                color: Colors.white,
                                borderRadius: BorderRadius.circular(20.0),
                              ),
                              child: Padding(
                                padding: const EdgeInsets.symmetric(
                                  horizontal: 20.0,
                                  vertical: 1.0,
                                ),
                                child: Column(
                                  crossAxisAlignment: CrossAxisAlignment.start,
                                  children: [
                                    const Text(
                                      'Celular:',
                                      style: TextStyle(
                                        fontSize: 16.0,
                                        fontWeight: FontWeight.bold,
                                      ),
                                    ),
                                    TextField(
                                      controller: phoneController,
                                      decoration: const InputDecoration(
                                        border: InputBorder.none,
                                      ),
                                    )
                                  ],
                                ),
                              ),
                            ),
                            const SizedBox(height: 10.0),
                            ElevatedButton(
                              onPressed: _submitEmergency,
                              style: ElevatedButton.styleFrom(
                                foregroundColor: Colors.white,
                                backgroundColor: const Color(0xFF33DCDE),
                                textStyle: const TextStyle(
                                  fontFamily: 'Poppins',
                                  fontSize: 16.0,
                                  fontWeight: FontWeight.bold,
                                  color: Colors.white,
                                ),
                                shape: RoundedRectangleBorder(
                                  borderRadius: BorderRadius.circular(20.0),
                                ),
                                padding: const EdgeInsets.symmetric(
                                  vertical: 12.0,
                                  horizontal: 24.0,
                                ),
                              ),
                              child: const Text('Solicitar'),
                            ),
                          ],
                        ),
                      ),
                    ],
                  ),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }

  Future<String> uploadImageToFirebase(XFile? imageFile) async {
    String fileName = Path.basename(imageFile!.path);
    Reference storageReference =
        FirebaseStorage.instance.ref().child('images/$fileName');
    UploadTask uploadTask = storageReference.putFile(File(imageFile.path));
    TaskSnapshot taskSnapshot = await uploadTask.whenComplete(() => null);
    String downloadUrl = await taskSnapshot.ref.getDownloadURL();
    return downloadUrl;
  }

  void _submitEmergency() async {
    PermissionStatus status = await Permission.location.request();

    if (status.isGranted) {
      try {
        String userName = nameController.text;
        String userPhone = phoneController.text;
        Position position = await Geolocator.getCurrentPosition(desiredAccuracy: LocationAccuracy.high);
        GeoPoint geoPoint = GeoPoint(position.latitude, position.longitude);

        String? fotoBocaUrl = fotoBoca == null ? null : await uploadImageToFirebase(fotoBoca);
        String? fotoDocumentoUrl = fotoDocumento == null ? null : await uploadImageToFirebase(fotoDocumento);
        String? fotoCriancaUrl = fotoCrianca == null ? null : await uploadImageToFirebase(fotoCrianca);

        Map<String, dynamic> data = {
          'name': userName,
          'phone': userPhone,
          'fotoBoca': fotoBocaUrl,
          'fotoDocumento': fotoDocumentoUrl,
          'fotoCrianca': fotoCriancaUrl,
          'time': FieldValue.serverTimestamp(),
          'location': geoPoint,
        };
        var docRef = await db.collection("Emergencias").add(data);
        String docId = docRef.id;
        await db.collection("Emergencias").doc(docId).update({
          'id': docId,
        });
        await _navigator.push(
          MaterialPageRoute(builder: (context) => AcceptDentist(
            docId: docId,
          )),
        );
      } catch (error) {
        print('Não foi possível adicionar dados ao banco $error');
      }
    } else {
      if (status.isDenied) {
        Fluttertoast.showToast(
            msg: "Por favor, permita o acesso à localização para que possamos encontrar dentistas próximos a você.",
            toastLength: Toast.LENGTH_LONG,
            gravity: ToastGravity.BOTTOM,
            timeInSecForIosWeb: 1,
            backgroundColor: Colors.red,
            textColor: Colors.white,
            fontSize: 16.0
        );
      } else if (status.isPermanentlyDenied) {
        openAppSettings();
      }
    }
  }
}

class ContainerWithText extends StatefulWidget {
  final String buttonText;
  final String text;
  final Function(XFile?) onPhotoTaken;

  const ContainerWithText({
    Key? key,
    required this.buttonText,
    required this.text,
    required this.onPhotoTaken,
  }) : super(key: key);

  @override
  State<ContainerWithText> createState() => _ContainerWithTextState();
}

class _ContainerWithTextState extends State<ContainerWithText> {
  String? imagePath;

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: const EdgeInsets.all(20.0),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Text(
            widget.text,
            textAlign: TextAlign.left,
            style: const TextStyle(
              fontSize: 16.0,
              fontWeight: FontWeight.bold,
              color: Color(0xFF292929),
              fontFamily: 'Poppins',
            ),
          ),
          const SizedBox(height: 10.0),
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Container(
                  width: 100.0,
                  height: 100.0,
                  decoration: BoxDecoration(
                    color: Colors.white,
                    borderRadius: BorderRadius.circular(10.0),
                  ),
                  child: imagePath == null
                      ? Image.asset('assets/images/CAM.png', fit: BoxFit.cover)
                      : Image.file(File(imagePath!), fit: BoxFit.cover)),
              const SizedBox(width: 20.0),
              ElevatedButton(
                onPressed: () async {
                  final ImagePicker _picker = ImagePicker();
                  final XFile? photo =
                      await _picker.pickImage(source: ImageSource.camera);
                  if (photo != null) {
                    widget.onPhotoTaken(photo);
                    setState(() {
                      imagePath = photo.path;
                    });
                  }
                },
                style: ElevatedButton.styleFrom(
                  foregroundColor: Colors.white,
                  backgroundColor: const Color(0xFF33DCDE),
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(45.0),
                  ),
                  padding: const EdgeInsets.symmetric(
                    vertical: 12.0,
                    horizontal: 24.0,
                  ),
                ),
                child: Text(widget.buttonText),
              ),
            ],
          ),
        ],
      ),
    );
  }
}