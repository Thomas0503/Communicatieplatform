// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
import { getAuth } from "firebase/auth";
import { getFirestore } from "firebase/firestore";
import { getStorage } from "firebase/storage";
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
const firebaseConfig = {
  apiKey: "AIzaSyBO6tsIoEbbDt3uefDfmSNef9CLJZppC2E",
  authDomain: "communicatieplatform-711a9.firebaseapp.com",
  databaseURL: "https://communicatieplatform-711a9-default-rtdb.firebaseio.com",
  projectId: "communicatieplatform-711a9",
  storageBucket: "communicatieplatform-711a9.appspot.com",
  messagingSenderId: "188428600969",
  appId: "1:188428600969:web:fa103928e02b9de3d22858",
  measurementId: "G-HWPQ6QHN7L"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
const auth = getAuth(app);
const db = getFirestore(app);
const storage = getStorage(app);

export { auth, db, storage };
