import React, { useEffect, useState } from "react";
import { db, auth, storage } from "../firebase";
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
import User from "../components/User";
import MessageForm from "../components/MessageForm";
import Message from "../components/Message";
import Blog from "../components/Blog";


const D3 = () => {
    const [blogs, setBlogs] = useState([]);
    const [mean_stress, setStress] = useState({});
    const [count, setCount] = useState({});
    const [filter, setState] = useState("0");
    const user = auth.currentUser.uid;


    const handleDropdownChange = (e) => {
        setState(e.target.value);

    };





    useEffect(() => {
        console.log("effect")
        const dagboekRef = collection(db, "dagboekje", "dagboekje", user);
        // create query object
        const q = query(dagboekRef, orderBy("createdAt", "desc"));
        // execute query
        const unsub = onSnapshot(q, (querySnapshot) => {
            let blogs = [];
            let mean_stress = { "0": 0, "Bench": 0, "Dierenarts": 0, "Houdigen": 0, "Kinderen": 0, "Korte lijn zonder prikkels": 0, "Korte lijn met prikkels": 0, "Mee naar het werk": 0, "Op dingen springen / onder dingen kruipen": 0, "Stofzuiger": 0, "Tafeloefening": 0, "Voedselweigering": 0, "Vreemde ondergronden": 0, "Privé vervoer": 0, "Openbaar vervoer": 0, "Openbare plaatsen": 0, "Shopping": 0, "Horeca": 0, "Overige": 0 }
            let count = { "0": 0, "Bench": 0, "Dierenarts": 0, "Houdigen": 0, "Kinderen": 0, "Korte lijn zonder prikkels": 0, "Korte lijn met prikkels": 0, "Mee naar het werk": 0, "Op dingen springen / onder dingen kruipen": 0, "Stofzuiger": 0, "Tafeloefening": 0, "Voedselweigering": 0, "Vreemde ondergronden": 0, "Privé vervoer": 0, "Openbaar vervoer": 0, "Openbare plaatsen": 0, "Shopping": 0, "Horeca": 0, "Overige": 0 }
            querySnapshot.forEach((doc) => {
                mean_stress[doc.data().oefening] = parseInt(mean_stress[doc.data().oefening]) + parseInt(doc.data().stressniveau);
                count[doc.data().oefening] += 1;
                if (doc.data().oefening == filter || filter == "0") {
                    blogs.push(doc.data());
                }


            });
            setBlogs(blogs);
            setStress(mean_stress);
            setCount(count);


        });
        return () => unsub();
    }, []);



    const handleSubmit = async (e) => {
        e.preventDefault();
        const dagboekRef = collection(db, "dagboekje", "dagboekje", user);
        // create query object
        const q = query(dagboekRef, orderBy("createdAt", "desc"));
        // execute query
        const unsub = onSnapshot(q, (querySnapshot) => {
            let blogs = [];
            let mean_stress = { "0": 0, "Bench": 0, "Dierenarts": 0, "Houdigen": 0, "Kinderen": 0, "Korte lijn zonder prikkels": 0, "Korte lijn met prikkels": 0, "Mee naar het werk": 0, "Op dingen springen / onder dingen kruipen": 0, "Stofzuiger": 0, "Tafeloefening": 0, "Voedselweigering": 0, "Vreemde ondergronden": 0, "Privé vervoer": 0, "Openbaar vervoer": 0, "Openbare plaatsen": 0, "Shopping": 0, "Horeca": 0, "Overige": 0 }
            let count ={ "0": 0, "Bench": 0, "Dierenarts": 0, "Houdigen": 0, "Kinderen": 0, "Korte lijn zonder prikkels": 0, "Korte lijn met prikkels": 0, "Mee naar het werk": 0, "Op dingen springen / onder dingen kruipen": 0, "Stofzuiger": 0, "Tafeloefening": 0, "Voedselweigering": 0, "Vreemde ondergronden": 0, "Privé vervoer": 0, "Openbaar vervoer": 0, "Openbare plaatsen": 0, "Shopping": 0, "Horeca": 0, "Overige": 0 }
            querySnapshot.forEach((doc) => {
                mean_stress[doc.data().oefening] = parseInt(mean_stress[doc.data().oefening]) + parseInt(doc.data().stressniveau);
                count[doc.data().oefening] += 1;
                if (doc.data().oefening == filter || filter == "0") {
                    blogs.push(doc.data());
                }


            });
            setBlogs(blogs);
            setStress(mean_stress);
            setCount(count);
            console.log(mean_stress);
        });
        return () => unsub();
    }




    return (
        <div className="dagboek2_container">

            <div className="filter_form">

                <form onSubmit={handleSubmit}>
                    <select onChange={handleDropdownChange}>
                        <option value="0">Kies filter</option>
                        <option value="Bench">Bench</option>
                        <option value="Dierenarts">Dierenarts</option>
                        <option value="Houdigen">Houdigen</option>
                        <option value="Kinderen">Kinderen</option>
                        <option value="Korte lijn zonder prikkels">Korte lijn zonder prikkels</option>
                        <option value="Korte lijn met prikkels">Korte lijn met prikkels</option>
                        <option value="Mee naar het werk">Mee naar het werk</option>
                        <option value="Op dingen springen / onder dingen kruipen">Op dingen springen / onder dingen kruipen</option>
                        <option value="Stofzuiger">Stofzuiger</option>
                        <option value="Tafeloefening">Tafeloefening</option>
                        <option value="Voedselweigering">Voedselweigering</option>
                        <option value="Vreemde ondergronden">Vreemde ondergronden</option>
                        <option value="Privé vervoer">Privé vervoer</option>
                        <option value="Openbaar vervoer">Openbaar vervoer</option>
                        <option value="Openbare plaatsen">Openbare plaatsen</option>
                        <option value="Shopping">Shopping</option>
                        <option value="Horeca">Horeca</option>
                 

                    </select>
                    <div>
                        <button className="btn">Filter</button>
                    </div>
                </form>
                {/* Einde dagboek form */}

            </div>
            <div className="blogs_container">
                {blogs.map((blog) => (

                    <Blog
                        blog={blog}


                    />
                ))}
            </div>


            <div className="stats_container">



               
                
                <h2>Gemiddeld stressniveau per oefening</h2>
               
                <div>
                    {
                        Object.keys(mean_stress).map((key, index) => (
                            <h3>{count[key] == 0? "":key +": "+ mean_stress[key] / count[key]}</h3>
                        ))
                    }
                </div>

            </div>






        </div>
    );
};

export default D3;
