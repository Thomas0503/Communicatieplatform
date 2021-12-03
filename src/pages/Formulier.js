import React, { useEffect, useState } from "react";
import { signInWithEmailAndPassword } from "firebase/auth";
import { auth, db, storage } from "../firebase";
import { useHistory } from "react-router-dom";
import {
  collection,
  query,
  where,
  onSnapshot,
  addDoc,
  Timestamp,
  orderBy,
  setDoc,
  doc,
  getDoc,
  updateDoc,
} from "firebase/firestore";
import { ref, getDownloadURL, uploadBytes } from "firebase/storage";

const Formulier = () => {

  const [links, setLinks] = useState([]);
  const [sizes, setSizes] = useState([]);

  const [img, setImg] = useState("");
  const [loading, setLoading] = useState(false);
  const history = useHistory();

  const user = auth.currentUser.uid;
  console.log(auth.currentUser.getIdToken())

  useEffect(() => {


    getLinks();



  }, []);






  var dlUrl = "";
  const handleSubmit = async (e) => {
    e.preventDefault();
    if (img) {
      const imgRef = ref(
        storage,
        `pdfs/${new Date().getTime()} - ${img.name}`
      );
      console.log("begin upload")
      setLoading(true)
      const snap = await uploadBytes(imgRef, img);
      dlUrl = await getDownloadURL(ref(storage, snap.ref.fullPath));
      setLoading(false)
      console.log("path")
      console.log(snap.ref.fullPath)

      //window.open(dlUrl);
      getLinks()
      setImg("");
    }




    await addDoc(collection(db, "formulier", "formulier", user), {
      "uid": user,
      createdAt: Timestamp.fromDate(new Date()),
      link: dlUrl,
      name: img.name,
      size: img.size

    });
    setImg("")
    

  };



  const getLinks = async () => {
    let links = {};
    let sizes= {};
    // create query object
    // execute query

    const formulierRef = collection(db, "formulier", "formulier", user);
    // create query object
    const q = query(formulierRef, orderBy("createdAt", "desc"));
    // execute query
    const unsub = onSnapshot(q, (querySnapshot) => {

      querySnapshot.forEach((doc) => {
        console.log(doc.data().link)
        links[doc.data().link]= doc.data().name;
        if (doc.data().size < 1000){
          sizes[doc.data().link] = doc.data().size + "B"
        } else if(doc.data().size < 1000000){
          var kb = Math.round(doc.data().size/1000)
          sizes[doc.data().link] = kb + "kB"
        } else { 
          var mb = Math.round(doc.data().size/1000000)
          sizes[doc.data().link] = mb + "MB"
        }
        
      })
      setLinks(links)
      setSizes(sizes)
      //console.log(links)
    });
    return () => unsub();
    
  }






  return (
    <section>
      <h3>Formulieren</h3>
      

      <div>
        


        
        <div>
          {
            Object.keys(links).map((key, index) => ( 
              <>
            <h1>{links[key]}</h1>
            <button onClick={() => window.open(key)}>download</button>
            size: {sizes[key]}
          </>
            ))
          }
        </div>


      </div>
    </section>
  );
};

export default Formulier;
