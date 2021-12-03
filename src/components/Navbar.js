import React, { useContext, useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { auth, db } from "../firebase";
import { signOut } from "firebase/auth";
import { updateDoc, doc, query, where, collection, onSnapshot, getDoc } from "firebase/firestore";
import { AuthContext } from "../context/auth";
import { useHistory } from "react-router-dom";

const Navbar = () => {

  const history = useHistory();
  const { user } = useContext(AuthContext);

  const [trainer, setTrainer] = useState(true)


  const getTrainer = async () => {

    try{
    const docSnap = await getDoc(doc(db, "users", auth.currentUser.uid));
    
    if (docSnap.exists()) {
      console.log("Document data:", docSnap.data());
      setTrainer(docSnap.data().trainer);
    } else {
      // doc.data() will be undefined in this case
      console.log("No such document!");
    }
  
  }catch (e) {
    console.log('niet ingelogd')
    }
}

  const handleSignout = async () => {
    await updateDoc(doc(db, "users", auth.currentUser.uid), {
      isOnline: false,
    });
    await signOut(auth);
    history.replace("/login");
  };

  //useEffect(() => {
    getTrainer()
  //  console.log(user)
   // }, []);
 
  return (
    <nav>
      
      
        {user ? (
          

         
          <>
         
          {trainer==true ? (<>

            <h3>
        <Link to="/chat_trainer">Chat</Link>
      </h3>

            <div>
          <Link to="/dagboekje_get_trainer">Dagboekje</Link> 
          <Link to="/calT">calender</Link>
          <Link to="/formulier">Documenten</Link>
          Trainer
          <button className="btn" onClick={handleSignout}>
              Logout
            </button>
          </div>
          </>
          
          
          ):(
          
          <>
          <h3>
        <Link to="/chat">Chat</Link>
      </h3>
          <div>
          
          <Link to="/dagboek2">dagboek</Link>
          <Link to="/calender">Kalender</Link>
          <Link to="/Form3">Documenten</Link>
          <button className="btn" onClick={handleSignout}>
              Logout
            </button>
          </div>
          </>)}
            
            
             </>
            
          
        ) : (
          <>
            <Link to="/register">Register</Link>
            <Link to="/login">Login</Link>
          </>
        )}
    </nav>
  );
};

export default Navbar;
