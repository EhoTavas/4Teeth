import 'package:flutter/material.dart';
import 'package:flutter_rating_bar/flutter_rating_bar.dart';

class AvaliarAtendimentoScreen extends StatefulWidget {
  const AvaliarAtendimentoScreen({super.key});

  @override
  State<AvaliarAtendimentoScreen> createState() => _AvaliarAtendimentoScreenState();
}

class _AvaliarAtendimentoScreenState extends State<AvaliarAtendimentoScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: const Text(
            'Avaliar atendimento',
            style: TextStyle(
              fontSize: 20,
              fontWeight: FontWeight.bold,
            ),
          ),
          centerTitle: true,
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
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      const Text(
                        'De uma nota de 0 a 5 estrelas para o profissional',
                        textAlign: TextAlign.center,
                        style: TextStyle(
                          fontSize: 18,
                        ),
                      ),
                      const SizedBox(height: 10),
                      RatingBar.builder(
                        initialRating: 0,
                        minRating: 0,
                        direction: Axis.horizontal,
                        allowHalfRating: true,
                        itemCount: 5,
                        itemSize: 30,
                        itemPadding: const EdgeInsets.symmetric(horizontal: 4.0),
                        itemBuilder: (context, _) => const Icon(
                          Icons.star,
                          color: Colors.yellow,
                        ),
                        onRatingUpdate: (rating) {
                          // Lógica para atualizar a nota
                          print(rating);
                        },
                      ),
                      const SizedBox(height: 20),
                      const Text(
                        'Comente o que achou do atendimento em geral',
                        textAlign: TextAlign.center,
                        style: TextStyle(
                          fontSize: 18,
                        ),
                      ),
                      const SizedBox(height: 10),
                      Container(
                        width: double.infinity,
                        padding: const EdgeInsets.symmetric(horizontal: 20),
                        child: const TextField(
                          decoration: InputDecoration(
                            border: OutlineInputBorder(),
                            hintText: 'Digite seu comentário',
                          ),
                          maxLines: 5,
                        ),
                      ),
                      const SizedBox(height: 20),
                      ElevatedButton(
                        onPressed: () {
                          // Lógica para enviar os dados
                        },
                        child: const Text('Enviar'),
                      ),
                    ],
                  ),
                ),
              ],
            ),
          ),
        ),
    );
  }
}


