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


const D2 = () => {
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
          let mean_stress = {"0": 0, "1": 0, "2": 0, "3":0}
          let count = {"0": 0, "1": 0, "2": 0, "3":0}
          querySnapshot.forEach((doc) => {
            mean_stress[doc.data().oefening] = parseInt(mean_stress[doc.data().oefening])+ parseInt(doc.data().stressniveau);
            count[doc.data().oefening] += 1;
            if (doc.data().oefening == filter || filter == "0"){
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
          let mean_stress = {"0": 0, "1": 0, "2": 0, "3":0}
          let count = {"0": 0, "1": 0, "2": 0, "3":0}
          querySnapshot.forEach((doc) => {
            mean_stress[doc.data().oefening] = parseInt(mean_stress[doc.data().oefening])+ parseInt(doc.data().stressniveau);
            count[doc.data().oefening] += 1;
            if (doc.data().oefening == filter || filter == "0"){
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
            <option value="1">1</option>
            <option value="2">2</option>
            <option value="3">3</option>
            <option value="4">4</option>
          </select>
            <div>
              <button >Fiter</button>
            </div>
          </form>
          {/* Einde dagboek form */}
            
        </div>
        <div className="blogs_container">
        <h4>Dagboekje</h4>
          {blogs.map((blog) => (
              
            <Blog
              blog={blog}
            
              
            />
          ))}
      </div>


        <div className="stats_container">
          
         

          <div>
          {
            Object.keys(mean_stress).map((key, index) => ( 
              <h3>{key} : {mean_stress[key]}</h3>
            ))
          }
        </div>
        <div>
        {
          Object.keys(count).map((key, index) => ( 
            <h3>{key} : {count[key]}</h3>
          ))
        }
        </div>
        <h2>{filter}</h2>
        <h2>Gemiddeldes</h2>
        <div>
        {
          Object.keys(mean_stress).map((key, index) => ( 
            <h3>{key} : {mean_stress[key]/count[key]}</h3>
          ))
        }
        </div>
      
      </div>
      
      
    
      

        
      </div>
  );
};

export default D2;
