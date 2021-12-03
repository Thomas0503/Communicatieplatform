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


const BlogT = ({ blog }) => {
  const user = auth.currentUser.uid;

  
  
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
      
  
        </div>
        
      
    </>
  );
};

export default BlogT;
