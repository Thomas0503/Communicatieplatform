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

const Form2 = () => {

    const [links, setLinks] = useState([]);

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
            link: dlUrl,
            RRef: RRef,

        });



    };



    const getLinks = async () => {
        let links = [];

        // create query object
        // execute query

        const formulierRef = collection(db, "documents");
        // create query object
        const q = query(formulierRef);
        // execute query
        const unsub = onSnapshot(q, (querySnapshot) => {
            var dd = "";
            querySnapshot.forEach((doc) => {
                console.log(doc.data().link);
                //dlUrl = await getDownloadURL(ref(storage, snap.ref.fullPath));
                //dd = getDownloadURL(doc.data().url);
                console.log(doc.data().url)
                dd = doc.data().url
                //console.log(getDownloadURL(doc.data().url))
                links.push(doc.data().url);

            })

            setLinks(links)
            getDLlinks()
            //console.log(links)
        });
        return () => unsub();
    }


    const getDLlinks = async () => {
        var d = ""
        var L = links.length
        links.map((link) => (
            console.log(link)
        ))



        console.log(d)

        //window.open(d);

    }





    return (
        <section>
            <h3>Add doc</h3>
            <form className="form" onSubmit={handleSubmit}>

                <input
                    onChange={(e) => setImg(e.target.files[0])}
                    type="file"
                    id="img"
                    accept="application/msword, application/pdf, image/*"
                    style={{ display: "box" }}
                />
                <div className="btn_container">
                    <button className="btn" >
                        Voeg toe
                    </button>
                </div>

            </form>

            <div>
                kies getDownload


                {links.map((link) => (
                    <>
                        <h1>{link}</h1>
                        <button onClick={() => window.open(link)}>download</button>
                    </>
                ))}


            </div>
        </section>
    );
};

export default Form2;
