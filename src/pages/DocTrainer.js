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

const DocTrainer = () => {

    const [links, setLinks] = useState([]);

    const [img, setImg] = useState("");
    const [loading, setLoading] = useState(false);
    const [gezinnen, setGezinnen] = useState([]);
    const [names, setNames] = useState([]);
    const history = useHistory();
    const [sizes, setSizes] = useState([]);


    const user = auth.currentUser.uid;

    useEffect(() => {


        getLinks();
        handleGez();


    }, []);

    const [filter, setState] = useState("0");
    const [filter2, setState2] = useState("-1");

    const namesHard = { "EqI10LALkGOjjonWT9LGSUIdc572": "andee pc", "wVEKX0xL6xRiSvztD1EFV3gHos12": "tt" }

    const handleDropdownChange2 = (e) => {
        setState2(e.target.value);
        console.log(filter2)
    };

    const handleDropdownChange = (e) => {
        setState(e.target.value);
        console.log(filter)
    };

 




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


        console.log("filter")
        console.log(filter)


        var c = [filter]
        if (filter == "-1") {
            c = gezinnen
        }
        c.map((object, i) => {

            addDoc(collection(db, "formulier", "formulier", object), {
                "uid": user,
                createdAt: Timestamp.fromDate(new Date()),
                link: dlUrl,
                name: img.name,
                size: img.size,

            });
        })



        setImg("")

    };



    const getLinks = async () => {
        let links = {};

        // create query object
        // execute query
        console.log(filter2)
        if (filter2 != "-1"){

        
        const formulierRef = collection(db, "formulier", "formulier", filter2);
        // create query object
        const q = query(formulierRef,  orderBy("createdAt", "desc"));
        // execute query
        const unsub = onSnapshot(q, (querySnapshot) => {

            querySnapshot.forEach((doc) => {
                console.log(doc.data().link)
                links[doc.data().link] = doc.data().name;

                if (doc.data().size < 1000) {
                    sizes[doc.data().link] = doc.data().size + "B"
                } else if (doc.data().size < 1000000) {
                    var kb = Math.round(doc.data().size / 1000)
                    sizes[doc.data().link] = kb + "kB"
                } else {
                    var mb = Math.round(doc.data().size / 1000000)
                    sizes[doc.data().link] = mb + "MB"
                }

            })
            setLinks(links)
            //console.log(links)
            
        });

        return () => unsub();
    }
    }



    const handleGez = async () => {

        var gezinnen1 = [];

        // create query object
        // execute query

        const usersRef = collection(db, "users");
        // create query object
        const q = query(usersRef, where("uid", "==", user));
        // execute query
        const unsub = onSnapshot(q, (querySnapshot) => {
            console.log("gezinnen")
            querySnapshot.forEach((doc) => {
                gezinnen1 = doc.data().pleeggezinnen;
                console.log(gezinnen1)

            })
            setGezinnen(gezinnen1);
            let names1 = [];
            const usersRef2 = collection(db, "users");
            const q2 = query(usersRef2, where("uid", "in", gezinnen1));
            const unsub2 = onSnapshot(q2, (querySnapshot) => {

                querySnapshot.forEach((doc) => {
                    names1.push(doc.data().name);


                })
                setNames(names1);
                console.log(names1)
            });
        });




        return () => unsub();

    }


    return (
        <section>

            <div className="filter_form">
            <div className="btn_container">
                Bekijk documenten van
                <select onChange={handleDropdownChange2}>

                    {gezinnen.map((object, i) =>
                        <option value={object}>{namesHard[object]}</option>

                    )}

                </select>



                
                    <button className="btn" disabled={loading} onClick={getLinks}>
                        {loading ? "Aan het laden..." : "Filter"}
                    </button>
                </div>


            </div>



            <h3>Voeg document toe</h3>
            <form className="form" onSubmit={handleSubmit}>

                <input
                    onChange={(e) => setImg(e.target.files[0])}
                    type="file"
                    id="img"
                    accept="application/msword, application/pdf, image/*"
                    style={{ display: "box" }}
                />

                <select onChange={handleDropdownChange}>
                    {gezinnen.map((object, i) =>
                        <option value={object}>{namesHard[object]}</option>

                    )}
                    <option value="-1">Iedereen</option>

                </select>
                <div className="btn_container">
                    <button className="btn" >
                        Voeg toe
                    </button>
                </div>
            </form>

            <div>
                kies getDownload



                <div>
                    {
                        Object.keys(links).map((key, index) => (
                            <>
                                <h1>{links[key]}</h1>
                                <button onClick={() => window.open(key)}>download</button>
                                {sizes[key]}
                            </>
                        ))
                    }
                </div>


            </div>
        </section>
    );
};

export default DocTrainer;
