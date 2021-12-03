import React, { useRef, useEffect } from "react";
import Moment from "react-moment";
import reactImageSize from 'react-image-size';
import { db, auth, storage } from "../firebase";
import User from "../components/User";


import {
    collection,
    query,
    where,
    onSnapshot,
    addDoc,
    deleteDoc,
    Timestamp,
    orderBy,
    setDoc,
    doc,
    getDoc,
    updateDoc,
    CollectionReference,
  } from "firebase/firestore";


const Blog = ({ blog }) => {
  const user = auth.currentUser.uid;

  const delBlog = async() => {
    const calenderRef = collection(db, "dagboekje", "dagboekje", user);
    // create query object
    const q = query(calenderRef, where("createdAt", "==", blog.createdAt));
    const unsub = onSnapshot(q, (querySnapshot) => {
      
      querySnapshot.forEach((doc1) => {
      
        console.log(doc1.ref)
        console.log(doc1.ref._key.path.segments[8])
        //console.log(doc1.data())
        deleteDoc(doc(db, "dagboekje", "dagboekje", user, doc1.ref._key.path.segments[8]));
        console.log("deleted");
      })
      });
      return () => unsub();
  }
  
  return (
    <>
    
      <div className="jumbotron">
        <div className="afb">
          
        {blog.link ? 
    <img src={blog.link}/>
     : null} 
  </div>
  <div className="desc">
            <h3>{blog.desc}</h3>
            <h4>{blog.stressniveau}</h4>
            <h4>Oefening: {blog.oefening}</h4>
            <h4>Stresssignalen:</h4>
            {
        Object.keys(blog.stresssignalen).map((key, index) => ( 
          <h4>{blog.stresssignalen[key]}</h4>
        ))
      }
      
      <h4>{blog.createdAt.toDate().toLocaleTimeString('en-US', {hour: "numeric", minute: "numeric", hour12: false})}</h4>
      <h4>toegevoegd op {blog.createdAt.toDate().toLocaleDateString('nl-BE', {weekday: "long", month: "long", day: "2-digit", year: "numeric"})}</h4>
      </div>
      <button className="btn" onClick={delBlog}>
            verwijder
          </button>
  
        </div>
        
      
    </>
  );
};

export default Blog;
