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

const Form3 = () => {

    const [links, setLinks] = useState([]);
    const [links2, setLinks2] = useState({});
    const [durls, setDurls] = useState([]);
    const [sizes, setSizes] = useState({});
    const [names, setNames] = useState({});
    const [input, setInput] = useState("");


    const [img, setImg] = useState("");
    const [loading, setLoading] = useState(false);
    const history = useHistory();

    const user = auth.currentUser.uid;
    console.log(auth.currentUser.getIdToken())

    useEffect(() => {


        getLinks();



    }, []);






    var dlUrl = "";
    var RRef = "";
    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true)
        //setLinks([])
        if (img) {
            const imgRef = ref(
                storage,
                `pdfs/${new Date().getTime()} - ${img.name}`
            );
            console.log("begin upload")
            
            const snap = await uploadBytes(imgRef, img);

            dlUrl = await getDownloadURL(ref(storage, snap.ref.fullPath));

            RRef = snap.ref.fullPath
            console.log("path")
            console.log(snap.ref.fullPath)

            //window.open(dlUrl);
            getLinks()
            setImg("");
        }




        await addDoc(collection(db, "formulier", "formulier", user), {
            "uid": user,
            createdAt: Timestamp.fromDate(new Date()),
            link: RRef,
            name: img.name,
            size: img.size,

        });
        setLoading(false)




    };



    const getLinks = async () => {
        let links = [];
        let durls = [];
        let links2 = {}
        let sizes = {}
        let names = {}
        // create query object
        // execute query

        const formulierRef = collection(db, "formulier", "formulier", user);
        // create query object
        const q = query(formulierRef, orderBy("createdAt", "desc"));
        // execute query
        const unsub = onSnapshot(q, (querySnapshot) => {
            var dd = "";
            querySnapshot.forEach((doc) => {
                console.log(doc.data().link);
                //dlUrl = await getDownloadURL(ref(storage, snap.ref.fullPath));
                //dd = getDownloadURL(doc.data().url);
                //sizes[doc.data().link] = doc.data().size
                names[doc.data().link] = doc.data().name
                if (doc.data().size < 1000){
                    sizes[doc.data().link] = doc.data().size + "B"
                  } else if(doc.data().size < 1000000){
                    var kb = Math.round(doc.data().size/1000)
                    sizes[doc.data().link] = kb + "kB"
                  } else { 
                    var mb = Math.round(doc.data().size/1000000)
                    sizes[doc.data().link] = mb + "MB"
                  }
                // Get the download URL
                getDownloadURL(ref(storage, doc.data().link))
                    .then((url) => {
                        durls.push(url)
                        links2[doc.data().link] = url
                        
                        //console.log(url)

                        // Insert url into an <img> tag to "download"
                    })
                //console.log(getDownloadURL(doc.data().url))
                links.push(doc.data().link);

            })

            setLinks(links)
            setDurls(durls)
            setLinks2(links2)
            setSizes(sizes)
            setNames(names)
            console.log(durls)
            console.log(links2)
            //getDLlinks()
            console.log("end")
            //console.log(links)
        });
        return () => unsub();
    }


    const getDLlinks = async () => {
        var d = ""
        var L = links.length
        console.log(links)
        links.map((link) => (
            console.log(link)
        ))



        console.log(d)

        //window.open(d);

    }

    const handleChange = (e) => {
        setInput(e.target.value);
      };

      const handleZoek = (e) => {
        let links = [];
        let durls = [];
        let links2 = {}
        let sizes = {}
        let names = {}
        // create query object
        // execute query

        const formulierRef = collection(db, "formulier", "formulier", user);
        // create query object
        const q = query(formulierRef, orderBy("createdAt", "desc"));
        // execute query
        
        const unsub = onSnapshot(q, (querySnapshot) => {
            var dd = "";
            querySnapshot.forEach((doc) => {
                if(doc.data().name.includes(input) ){
                console.log(doc.data().link);
                //dlUrl = await getDownloadURL(ref(storage, snap.ref.fullPath));
                //dd = getDownloadURL(doc.data().url);
                //sizes[doc.data().link] = doc.data().size
                names[doc.data().link] = doc.data().name
                if (doc.data().size < 1000){
                    sizes[doc.data().link] = doc.data().size + "B"
                  } else if(doc.data().size < 1000000){
                    var kb = Math.round(doc.data().size/1000)
                    sizes[doc.data().link] = kb + "kB"
                  } else { 
                    var mb = Math.round(doc.data().size/1000000)
                    sizes[doc.data().link] = mb + "MB"
                  }
                // Get the download URL
                getDownloadURL(ref(storage, doc.data().link))
                    .then((url) => {
                        durls.push(url)
                        links2[doc.data().link] = url
                        
                        //console.log(url)

                        // Insert url into an <img> tag to "download"
                    })
                //console.log(getDownloadURL(doc.data().url))
                links.push(doc.data().link);
                }
            })

            setLinks(links)
            setDurls(durls)
            setLinks2(links2)
            setSizes(sizes)
            setNames(names)
            console.log(durls)
            console.log(links2)
            //getDLlinks()
            console.log("end")
            //console.log(links)
        });
        return () => unsub();
      };



    return (
        <div className="doc_container">
            
            
            



              <div className="doc_filter">

              <input
            type="text"
            name="input"
            value={input}
            onChange={handleChange}
            autoComplete="off"
          />
                <div className>
                <button className="btn" disabled={loading} onClick={handleZoek}>
            {loading ? "Aan het zoeken..." : "Zoek"}
          </button>
                </div>
            </div>

            <div className="doc_vis">


                {links.map((link) => (
                    <>
                        <h3>{names[link]}</h3>
                        
                        <button onClick={() => window.open(links2[link])}>download</button>
                        {sizes[link]}
                    </>
                ))}

           


            </div>
        </div>
    );
};

export default Form3;
