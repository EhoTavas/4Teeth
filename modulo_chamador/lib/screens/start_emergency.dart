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

// import 'package:flutter/material.dart';
//
//
// void main() {
//   runApp(const MyApp());
// }
//
// class MyApp extends StatelessWidget {
//   const MyApp({super.key});
//
//   @override
//   Widget build(BuildContext context) {
//     return MaterialApp(
//       home: Scaffold(
//         appBar: AppBar(
//           backgroundColor: const Color(0xFFF0FAF9),
//           centerTitle: true,
//           title: const Text('Solicitar Socorro',
//             style: TextStyle(
//               color: Color(0xFF292929),
//               fontFamily: 'Poppins',
//             ),),
//         ),
//         body: Container(
//           margin: const EdgeInsets.only(top: 20.0),
//           decoration: const BoxDecoration(
//             gradient: RadialGradient(
//               colors: [
//                 Color(0xFF24B8B8), // #24B8B8
//                 Color(0xFFF0FAF9), // #f0faf9
//               ],
//             ),
//           ),
//           child: SingleChildScrollView(
//             child: Column(
//               children: [
//                 Container(
//                   decoration: const BoxDecoration(
//                     color: Color(0xFFA8E9EB),
//                     borderRadius: BorderRadius.only(
//                       topLeft: Radius.circular(45.0),
//                       topRight: Radius.circular(45.0),
//                     ),
//                   ),
//                   padding: const EdgeInsets.all(7.0),
//                   child: Column(
//                     children: [
//                       const ContainerWithText(
//                         imagePath: 'caminho/para/a/imagem1.png',
//                         buttonText: 'Tirar Foto',
//                         text: 'Fotografe a região/boca acidentada:',
//                       ),
//                       const ContainerWithText(
//                         imagePath: 'caminho/para/a/imagem2.png',
//                         buttonText: 'Tirar Foto',
//                         text: 'Envie uma foto do documento do responsável:',
//                       ),
//                       const ContainerWithText(
//                         imagePath: 'caminho/para/a/imagem3.png',
//                         buttonText: 'Tirar Foto',
//                         text: 'Tire uma foto com a criança:',
//                       ),
//                       const SizedBox(height: 1.0),
//                       Container(
//                         padding: const EdgeInsets.all(0.0),
//                         margin: const EdgeInsets.all(10.0),
//                         child: Column(
//                           children: [
//                             Container(
//                                 decoration: BoxDecoration(
//                                   color: Colors.white,
//                                   borderRadius: BorderRadius.circular(10.0),
//                                 ),
//                                 child: const Padding(
//                                   padding: EdgeInsets.all(10.0),
//                                   child: Column(
//                                     crossAxisAlignment: CrossAxisAlignment.start,
//                                     children: [
//                                       Text(
//                                         'Nome:',
//                                         style: TextStyle(
//                                           fontSize: 16.0,
//                                           fontWeight: FontWeight.bold,
//                                         ),
//                                       ),
//                                       TextField(
//                                         decoration: InputDecoration(
//                                           border: InputBorder.none,
//                                         ),
//                                       ),
//                                     ],
//                                   ),
//                                 ))
//                           ],
//                         ),
//                       ),
//                     ],
//                   ),
//                 ),
//               ],
//             ),
//           ),
//         ),
//       ),
//     );
//   }
// }
//
// class ContainerWithText extends StatelessWidget {
//   final String imagePath;
//   final String buttonText;
//   final String text;
//
//   const ContainerWithText({
//     Key? key,
//     required this.imagePath,
//     required this.buttonText,
//     required this.text,
//   }) : super(key: key);
//
//   @override
//   Widget build(BuildContext context) {
//     return Container(
//       padding: const EdgeInsets.all(20.0),
//       child: Column(
//         crossAxisAlignment: CrossAxisAlignment.start,
//         children: [
//           Text(
//             text,
//             textAlign: TextAlign.center,
//             style: const TextStyle(
//               fontSize: 16.0,
//               fontWeight: FontWeight.bold,
//               color: Color(0xFF292929),
//               fontFamily: 'Poppins',
//             ),
//           ),
//           const SizedBox(height: 10.0),
//           Row(
//             mainAxisAlignment: MainAxisAlignment.center,
//             children: [
//               Container(
//                 width: 100.0,
//                 height: 100.0,
//                 decoration: BoxDecoration(
//                   color: Colors.white,
//                   borderRadius: BorderRadius.circular(10.0),
//                 ),
//                 child: Image.asset(
//                   imagePath,
//                   fit: BoxFit.contain,
//                 ),
//               ),
//               const SizedBox(width: 20.0),
//               ElevatedButton(
//                 onPressed: () {
//                   // Lógica para abrir a câmera
//                 },style: ElevatedButton.styleFrom(
//                 foregroundColor: Colors.white,
//                 backgroundColor: const Color(0xFF33DCDE),
//                 shape: RoundedRectangleBorder(
//                   borderRadius: BorderRadius.circular(45.0),
//                 ),
//                 padding: const EdgeInsets.symmetric(
//                   vertical: 12.0,
//                   horizontal: 24.0,
//                 ),
//               ),
//                 child: Text(buttonText),
//               ),
//             ],
//           ),
//         ],
//       ),
//     );
//   }
// }
