import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:flutter/material.dart';

class AcceptDentist extends StatefulWidget {
  final String docId;
  const AcceptDentist({super.key, required this.docId});

  @override
  State<AcceptDentist> createState() => _AcceptDentistState();
}

class _AcceptDentistState extends State<AcceptDentist> {
  List<DocumentSnapshot> dentists = [];

  Future<void> refreshData() async {
    dentists = await getData();
    setState(
        () {}); // Atualiza a interface do usuário após a conclusão da recuperação dos dados.
  }

  Future<List<DocumentSnapshot>> getData() async {
    QuerySnapshot appointments = await FirebaseFirestore.instance
        .collection('Atendimentos')
        .where("emergency", isEqualTo: widget.docId)
        .where("status", isEqualTo: "1")
        .get();

    List<DocumentSnapshot> dentists = [];

    for (var doc in appointments.docs) {
      String dentistUid = doc['dentist'];
      QuerySnapshot dentistQuery = await FirebaseFirestore.instance
          .collection('Dentista')
          .where("uid", isEqualTo: dentistUid)
          .get();

      if (dentistQuery.docs.isNotEmpty) {
        dentists.add(dentistQuery.docs.first);
      }
    }

    return dentists;
  }

  @override
  void initState() {
    super.initState();
    refreshData(); // Carrega os dados inicialmente quando o widget é criado.
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          backgroundColor: const Color(0xFFF0FAF9),
          centerTitle: true,
          title: const Text(
            'Solicitar Profissional',
            style: TextStyle(
              color: Color(0xFF292929),
              fontFamily: 'Poppins',
            ),
          ),
        ),
        backgroundColor: const Color(0xFFF0FAF9),
        body: RefreshIndicator(
          onRefresh: refreshData,
          child: ListView.builder(
            itemCount: dentists.length,
            itemBuilder: (context, index) {
              var doc = dentists[index];
              return Center(
                child: Material(
                  elevation: 8,
                  shadowColor: const Color(0xFFC0ECE8),
                  borderRadius: BorderRadius.circular(35),
                  child: Container(
                    width: 318,
                    height: 125,
                    padding: const EdgeInsets.all(25),
                    decoration: BoxDecoration(
                      color: Colors.white,
                      borderRadius: BorderRadius.circular(35),
                    ),
                    child: Row(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Expanded(
                          child: Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              Text(
                                doc['nome'],
                                style: const TextStyle(fontSize: 18),
                              ),
                              const SizedBox(height: 10),
                              Row(
                                children: [
                                  Container(
                                    padding: const EdgeInsets.all(10),
                                    decoration: BoxDecoration(
                                      borderRadius: BorderRadius.circular(15),
                                      color: const Color(0xffb5ef55),
                                    ),
                                    child: const Row(
                                      mainAxisSize: MainAxisSize.min,
                                      children: [
                                        Icon(
                                          Icons.star,
                                          size: 20,
                                          color: Color(0xffe7ffbf),
                                        ),
                                        SizedBox(width: 5),
                                        Text(
                                          '4.5',
                                          style: TextStyle(
                                            fontSize: 16,
                                            color: Colors.white,
                                          ),
                                        ),
                                      ],
                                    ),
                                  ),
                                  const SizedBox(width: 10),
                                  Row(
                                    mainAxisSize: MainAxisSize.min,
                                    children: [
                                      const Icon(
                                        Icons.location_on,
                                        size: 20,
                                      ),
                                      const SizedBox(width: 5),
                                      Text(
                                        doc['cep1'], // Substitua 'distance' pelo campo correto do Firestore
                                        style: const TextStyle(fontSize: 16),
                                      ),
                                    ],
                                  ),
                                ],
                              ),
                            ],
                          ),
                        ),
                        const SizedBox(width: 20),
                        Container(
                          width: 50,
                          height: 50,
                          decoration: const BoxDecoration(
                            shape: BoxShape.circle,
                            color: Colors.blue,
                          ),
                          child: IconButton(
                            onPressed: () {
                              // Implementação do botão de ligação
                            },
                            icon: const Icon(
                              Icons.phone,
                              color: Colors.white,
                            ),
                          ),
                        ),
                      ],
                    ),
                  ),
                ),
              );
            },
          ),
        ),
      ),
    );
  }
}
