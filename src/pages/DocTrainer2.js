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

const DocTrainer2 = () => {

    const [links, setLinks] = useState([]);

    const [img, setImg] = useState("");
    const [loading, setLoading] = useState(false);
    const [gezinnen, setGezinnen] = useState([]);
    //const [names, setNames] = useState([]);
    const history = useHistory();
    //const [sizes, setSizes] = useState([]);
    const [links2, setLinks2] = useState({});
    const [durls, setDurls] = useState([]);
    const [sizes, setSizes] = useState({});
    const [names, setNames] = useState({});

    const user = auth.currentUser.uid;

    useEffect(() => {


        getLinks();
        handleGez();


    }, []);

    const [filter, setState] = useState("0");
    const [filter2, setState2] = useState("-1");
    const [input, setInput] = useState("");

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
            RRef = snap.ref.fullPath
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
                link: RRef,
                name: img.name,
                size: img.size,

            });
        })



        setImg("")

    };



    const getLinks = async () => {
        let links = [];
        let durls = [];
        let links2 = {}
        let sizes = {}
        let names = {}
        // create query object
        // execute query
        console.log(filter2)
        if (filter2 != "-1") {


            const formulierRef = collection(db, "formulier", "formulier", filter2);
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
                    if (doc.data().size < 1000) {
                        sizes[doc.data().link] = doc.data().size + "B"
                    } else if (doc.data().size < 1000000) {
                        var kb = Math.round(doc.data().size / 1000)
                        sizes[doc.data().link] = kb + "kB"
                    } else {
                        var mb = Math.round(doc.data().size / 1000000)
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
        if (filter2 != "-1"){
        const formulierRef = collection(db, "formulier", "formulier", filter2);
        // create query object
        const q = query(formulierRef, orderBy("createdAt", "desc"));
        // execute query

        const unsub = onSnapshot(q, (querySnapshot) => {
            var dd = "";
            querySnapshot.forEach((doc) => {
                if (doc.data().name.includes(input)) {
                    console.log(doc.data().link);
                    //dlUrl = await getDownloadURL(ref(storage, snap.ref.fullPath));
                    //dd = getDownloadURL(doc.data().url);
                    //sizes[doc.data().link] = doc.data().size
                    names[doc.data().link] = doc.data().name
                    if (doc.data().size < 1000) {
                        sizes[doc.data().link] = doc.data().size + "B"
                    } else if (doc.data().size < 1000000) {
                        var kb = Math.round(doc.data().size / 1000)
                        sizes[doc.data().link] = kb + "kB"
                    } else {
                        var mb = Math.round(doc.data().size / 1000000)
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
    }
    };



    return (
        <div className="doc_container">

            <div className="doc_filter">
                <div >
                    Bekijk documenten van
                    <select onChange={handleDropdownChange2}>

                        {gezinnen.map((object, i) =>
                            <option value={object}>{namesHard[object]}</option>

                        )}

                    </select>

                    <br />


                    <button className="btn" disabled={loading} onClick={getLinks}>
                        {loading ? "Aan het laden..." : "Filter"}
                    </button>
                </div>


            </div>
            <div className="doc_zoek">

                <input
                    type="text"
                    name="input"
                    value={input}
                    onChange={handleChange}
                    autoComplete="none"
                />
                <div>
                    <button className="btn" disabled={loading} onClick={handleZoek}>
                        {loading ? "Aan het laden..." : "Zoek"}
                    </button>
                </div>
            </div>



            <form className="doc_form" onSubmit={handleSubmit}>
                <h3>Voeg document toe</h3>
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
                <div>
                    <button className="btn" >
                        Voeg toe
                    </button>
                </div>
            </form>

            <div className="doc_vis">



                <div>

                    {links.map((link) => (
                        <>
                            <h1>{names[link]}</h1>

                            <button onClick={() => window.open(links2[link])}>download</button>
                            {sizes[link]}
                        </>
                    ))}
                </div>


            </div>
        </div>
    );
};

export default DocTrainer2;
