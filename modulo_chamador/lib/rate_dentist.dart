import 'package:flutter/material.dart';

class RatingScreen extends StatefulWidget {
  const RatingScreen({Key? key}) : super(key: key);

  @override
  _RatingScreenState createState() => _RatingScreenState();
}

class _RatingScreenState extends State<RatingScreen> {
  int rating = 0;

  void rateDentist(int newRating) {
    setState(() {
      rating = newRating;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: const Color(0xFFF0FAF9),
        centerTitle: true,
        title: const Text(
          'Avaliar Atendimento',
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
        child: Center(
          child: Container(
            width: 355,
            height: 285,
            margin: const EdgeInsets.all(20.0),
            decoration: BoxDecoration(
              color: Colors.white.withOpacity(0.72),
              borderRadius: BorderRadius.circular(20.0),
              boxShadow: [
                BoxShadow(
                  color: Colors.black.withOpacity(0.25),
                  blurRadius: 25.0,
                  offset: const Offset(15, 15),
                ),
              ],
            ),
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                const Text(
                  'Avalie o dentista:',
                  style: TextStyle(fontSize: 20),
                ),
                const SizedBox(height: 20),
                Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    GestureDetector(
                      onTap: () => rateDentist(1),
                      child: Icon(
                        rating >= 1 ? Icons.star : Icons.star_border,
                        size: 40,
                        color: const Color(0xFF5892DB),
                      ),
                    ),
                    GestureDetector(
                      onTap: () => rateDentist(2),
                      child: Icon(
                        rating >= 2 ? Icons.star : Icons.star_border,
                        size: 40,
                        color: const Color(0xFF5892DB),
                      ),
                    ),
                    GestureDetector(
                      onTap: () => rateDentist(3),
                      child: Icon(
                        rating >= 3 ? Icons.star : Icons.star_border,
                        size: 40,
                        color: const Color(0xFF5892DB),
                      ),
                    ),
                    GestureDetector(
                      onTap: () => rateDentist(4),
                      child: Icon(
                        rating >= 4 ? Icons.star : Icons.star_border,
                        size: 40,
                        color: const Color(0xFF5892DB),
                      ),
                    ),
                    GestureDetector(
                      onTap: () => rateDentist(5),
                      child: Icon(
                        rating >= 5 ? Icons.star : Icons.star_border,
                        size: 40,
                        color: const Color(0xFF5892DB),
                      ),
                    ),
                  ],
                ),
                const SizedBox(height: 20),
                Text(
                  'Sua avaliação: $rating estrelas',
                  style: const TextStyle(fontSize: 18),
                ),
                const SizedBox(height: 20),
                Container(
                  decoration: BoxDecoration(
                    borderRadius: BorderRadius.circular(20.0),
                    boxShadow: [
                      BoxShadow(
                        color: Colors.black.withOpacity(0.25),
                        blurRadius: 25.0,
                        offset: const Offset(15, 15),
                      ),
                    ],
                  ),
                  child: ElevatedButton(
                    onPressed: () {
                      // Aqui você pode adicionar a lógica para enviar a avaliação do dentista
                      // para um serviço ou fazer outras ações desejadas.
                      print('Avaliação enviada: $rating estrelas');
                    },
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
                    child: const Text('Enviar'),
                  ),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}

void main() {
  runApp(MaterialApp(
    home: const RatingScreen(),
    theme: ThemeData(
      primaryColor: Colors.teal[400],
    ),
  ));
}
