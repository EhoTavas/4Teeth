import 'package:firebase_core/firebase_core.dart';
import 'package:flutter/material.dart';
import 'package:cloud_firestore/cloud_firestore.dart';

import 'firebase_options.dart';

//Tela inicial
Future<void> main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await Firebase.initializeApp(options: DefaultFirebaseOptions.currentPlatform);
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
                      const ContainerWithText(
                        imagePath: 'assets/images/CAM.png',
                        buttonText: 'Tirar Foto',
                        text: 'Fotografe a região/boca acidentada:',
                      ),
                      const ContainerWithText(
                        imagePath: 'assets/images/CAM.png',
                        buttonText: 'Tirar Foto',
                        text: 'Envie uma foto do documento do responsável:',
                      ),
                      const ContainerWithText(
                        imagePath: 'assets/images/CAM.png',
                        buttonText: 'Tirar Foto',
                        text: 'Tire uma foto com a criança:',
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

  void _submitEmergency() async {
    try {
      String userName = nameController.text;
      String userPhone = phoneController.text;
      // Define os dados que serão enviados
      Map<String, dynamic> data = {
        'name': userName,
        'phone': userPhone,
        'time': FieldValue.serverTimestamp(),
      };

      await db.collection("Emergencias").add(data).then((documentSnapshot) =>
          print("Added Data with ID: ${documentSnapshot.id}"));
    } catch (error) {
      print('deu merda aqui $error');
    }
  }
}

class ContainerWithText extends StatelessWidget {
  final String imagePath;
  final String buttonText;
  final String text;

  const ContainerWithText({
    Key? key,
    required this.imagePath,
    required this.buttonText,
    required this.text,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: const EdgeInsets.all(20.0),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Text(
            text,
            textAlign: TextAlign.center,
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
                child: Image.asset(
                  imagePath,
                  fit: BoxFit.contain,
                ),
              ),
              const SizedBox(width: 20.0),
              ElevatedButton(
                onPressed: () {
                  // Lógica para abrir a câmera
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
                child: Text(buttonText),
              ),
            ],
          ),
        ],
      ),
    );
  }
}

//
// Future<void> main() async {
//   WidgetsFlutterBinding.ensureInitialized();
//   await Firebase.initializeApp(options: DefaultFirebaseOptions.currentPlatform);
//   final cameras = await availableCameras();
//   final firstCamera = cameras.first;
//   // Create a storage reference from our app
//   runApp(
//     MaterialApp(
//       theme: ThemeData.dark(),
//       home: TakePictureScreen(
//         // Pass the appropriate camera to the TakePictureScreen widget.
//         camera: firstCamera,
//       ),
//     ),
//   );
// }
//
// class TakePictureScreen extends StatefulWidget {
//   const TakePictureScreen({super.key, required this.camera});
//
//   final CameraDescription camera;
//
//   @override
//   TakePictureScreenState createState() => TakePictureScreenState();
// }
//
// class TakePictureScreenState extends State<TakePictureScreen> {
//   late CameraController _controller;
//   late Future<void> _initializeControllerFuture;
//   late final NavigatorState _navigator;
//
//   @override
//   void initState() {
//     super.initState();
//     _navigator = Navigator.of(context);
//     // To display the current output from the Camera, create a CameraController.
//     _controller = CameraController(
//       // Get a specific camera from the list of available cameras.
//       widget.camera,
//       //Define the resolution to use.
//       ResolutionPreset.high,
//     );
//
//     // Next, initialize the controller. This returns a Future.
//     _initializeControllerFuture = _controller.initialize();
//   }
//
//   @override
//   void dispose() {
//     // Dispose of the controller when the widget is disposed.
//     _controller.dispose();
//     super.dispose();
//   }
//
//   @override
//   Widget build(BuildContext context) {
//     // Fill this out in the next steps.
//     return Scaffold(
//       appBar: AppBar(title: const Text('Take a picture')),
//       // You must wait until the controller is initialized before displaying the camera preview.
//       // Use a FutureBuilder to display a loading spinner until the controller has finished initializing.
//       body: FutureBuilder<void>(
//         future: _initializeControllerFuture,
//         builder: (context, snapshot) {
//           if (snapshot.connectionState == ConnectionState.done) {
//             // If the Future is complete, display the preview.
//             return CameraPreview(_controller);
//           } else {
//             // Otherwise, display a loading indicator.
//             return const Center(child: CircularProgressIndicator());
//           }
//         },
//       ),
//       floatingActionButton: FloatingActionButton(
//         onPressed: _takePhoto,
//         child: const Icon(Icons.camera_alt),
//       ),
//     );
//   }
//
//   Future<String> uploadImageToFirebase(File imageFile) async {
//     String fileName = Path.basename(imageFile.path);
//     Reference storageReference =
//         FirebaseStorage.instance.ref().child('images/$fileName');
//     UploadTask uploadTask = storageReference.putFile(imageFile);
//     TaskSnapshot taskSnapshot = await uploadTask.whenComplete(() => null);
//     String downloadUrl = await taskSnapshot.ref.getDownloadURL();
//     return downloadUrl;
//   }
//
//   void _takePhoto() async {
//     // Take the Picture in a try / catch block. If anything goes wrong, catch the error.
//     try {
//       String imageUrl = '';
//       ImagePicker imagePicker = ImagePicker();
//       XFile? file = await imagePicker.pickImage(source: ImageSource.camera);
//
//       if (file == null) return;
//       String pictureName = DateTime.now().millisecondsSinceEpoch.toString();
//
//       Reference referenceRoot = FirebaseStorage.instance.ref();
//       Reference referenceDirImages = referenceRoot.child('images');
//
//       Reference referenceImageToUpload = referenceDirImages.child(pictureName);
//       try {
//         // Faz o upload da imagem para o Firebase Storage
//         await referenceImageToUpload.putFile(File(file.path));
//
//         imageUrl = await referenceImageToUpload.getDownloadURL();
//       } catch (error) {}
//       await _navigator.push(
//         MaterialPageRoute(
//           builder: (context) => DisplayPicture(
//             // Pass the automatically generated path to the DisplayPictureScreen widget.
//             imagePath: file.path,
//           ),
//         ),
//       );
//     } catch (e) {
//       // If an error occurs, log the error to the console.
//       print(e);
//     }
//   }
// }
//
// class MyApp extends StatelessWidget {
//   const MyApp({Key? key}) : super(key: key);
//
//   @override
//   Widget build(BuildContext context) {
//     return MaterialApp(
//       title: '',
//       theme: ThemeData(
//         primarySwatch: Colors.blue,
//       ),
//       home: const Splash(),
//     );
//   }
// }
