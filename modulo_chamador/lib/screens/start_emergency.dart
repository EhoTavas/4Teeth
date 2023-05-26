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
    final args = ModalRoute.of(context)!.settings.arguments as ScreenArguments;

    return Scaffold(
      appBar: AppBar(
        title: const Text('Solicitar Socorro'),
        centerTitle: true,
      ),
      body: Center(
          child: Padding(
            padding: const EdgeInsets.fromLTRB(24.0, 0.0, 24.0, 0.0),
            child: Column(
              mainAxisAlignment: MainAxisAlignment.spaceAround,
              children: [
                Column(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    TextFormField(
                      onChanged: (value) {
                        setState(() {
                          nome = value;
                        });
                      },
                      maxLength: 20,
                      decoration: const InputDecoration(
                        labelText: 'Nome:',
                        labelStyle: TextStyle(
                          color: Colors.black,
                        ),
                        focusedBorder: OutlineInputBorder(
                            borderSide: BorderSide(color: Colors.blue)),
                        enabledBorder: OutlineInputBorder(
                          borderSide: BorderSide(color: Colors.black38),
                        ),
                      ),
                    ),
                    const SizedBox(
                      height: 25,
                    ),
                    TextFormField(
                      onChanged: (value) {
                        setState(() {
                          telefone = value;
                        });
                      },
                      maxLength: 15,
                      decoration: const InputDecoration(
                        labelText: 'Telefone:',
                        labelStyle: TextStyle(
                          color: Colors.black,
                        ),
                        focusedBorder: OutlineInputBorder(
                            borderSide: BorderSide(color: Colors.blue)),
                        enabledBorder: OutlineInputBorder(
                          borderSide: BorderSide(color: Colors.black38),
                        ),
                      ),
                    ),
                    const SizedBox(
                      height: 25,
                    ),
                  ],
                ),
                ElevatedButton(
                    style: ButtonStyle(
                        padding: MaterialStateProperty.all(
                            const EdgeInsets.fromLTRB(46, 12, 46, 12))),
                    onPressed: () {
                      pedirsocorro(args.image).then((value) => {
                        Navigator.pushNamed(context, '/lista_aprovados',
                            arguments: ScreenArgumentsIdEmergencia(value))
                      });
                    },
                    child: const Text(
                      'Pedir socorro imediato',
                      style: TextStyle(fontSize: 18),
                    )),
              ],
            ),
          )),
    );
  }
}
