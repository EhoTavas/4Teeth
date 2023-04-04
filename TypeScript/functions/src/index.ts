import * as functions from "firebase-functions";
import * as admin from "firebase-admin";

const app = admin.initializeApp();
const db = app.firestore();
const colDentistas = db.collection("Dentista");

export const addDentist = functions
  .region("southamerica-east1")
  .https.onRequest(async (_request, response) => {
    const dentista = {
      Nome: "Matheus",
      Email: "Matheusfstaveira@gmail.com",
      Telefone: "(19)98235-0750",
      Endereco1: "Rua São Jorge",
      Endereco2: "Rua São José",
      Endereco3: "Rua São Joaquim",
      MiniCurriculo: "SimSimSimSimSimSalabim",
    };
    try {
      const docRef = await colDentistas.add(dentista);
      response.send("Usuário cadastrado. Referência:" + docRef.id);
    } catch (e) {
      functions.logger.error("Erro ao cadastrar usuário.");
      response.send("Erro ao cadastrar usuário.");
    }
  });
