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

const Dagboekje_get = () => {
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
          console.log(mean_stress);
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
        
      //dagboekje form    
      const [checked1, setChecked1] = useState(false);
  const [checked2, setChecked2] = useState(false);
 
  const handleChangeCheck1 = () => {
    setChecked1(!checked1);
  };

  const handleChangeCheck2 = () => {
    setChecked2(!checked2);
  };


  const [data, setData] = useState({
    desc: "",
    password: "",
    error: null,
    loading: false,
  });

  const [img, setImg] = useState("");
  const [selectValue, setState2] = useState("");

  
  const handleDropdownChange2 = (e) => {
    setState2(e.target.value);
  };

  const { desc, stress, error, loading } = data;


  

  const handleChange = (e) => {
    setData({ ...data, [e.target.name]: e.target.value });
  };
  var dlUrl = "";
  const handleSubmit2 = async (e) => {
    e.preventDefault();
    console.log(desc)
    if (img) {
      const imgRef = ref(
        storage,
        `dagboekje/${new Date().getTime()} - ${img.name}`
      );
      const snap = await uploadBytes(imgRef, img);
      console.log("toegevoegd");
      dlUrl = await getDownloadURL(ref(storage, snap.ref.fullPath));

      
      
    }

    await addDoc(collection(db, "dagboekje", "dagboekje", user), {
      "desc": desc,
      "uid": user,
      createdAt: Timestamp.fromDate(new Date()),
      stressniveau: stress,
      link: dlUrl,
      stresssignalen: {"Bevriezen": checked1.toString(), "Gapen": checked2.toString()},
      oefening: selectValue
      
    });
    setData({
      email: "",
      password: "",
      error: null,
      loading: false,
    });
    
  
  
  };

    
      
  return (
    <div className="dagboek_container">
      
        <div className="dagboek_form">
        
          <div>
              <h3>Dagboekje</h3>
              <form  onSubmit={handleSubmit2}>
                <div className="input_container">
                  <label htmlFor="text">Descr</label>
                  <input
                    type="text"
                    name="desc"
                    value={desc}
                    onChange={handleChange}
                  />
                </div>
                <input
                onChange={(e) => setImg(e.target.files[0])}
                type="file"
                id="img"
                accept="image/*"
                style={{ display: "box" }}
              />

                
            <div>
              <label>
                <input
                  type="checkbox"
                  checked={checked1}
                  onChange={handleChangeCheck1}
                />
                Bevriezen
              </label>
        
            </div>
            <div>
              <label>
                <input
                  type="checkbox"
                  checked={checked2}
                  onChange={handleChangeCheck2}
                />
                Gapen
              </label>
            </div>
            <select id = "dropdown" onChange={handleDropdownChange2}>
              <option value="0">Kies oefening</option>
              <option value="1">1</option>
              <option value="2">2</option>
              <option value="3">3</option>
              <option value="4">4</option>
        </select>
                
                <div>
                <input type="number" max="100"placeholder = "Stressnivea"
                name="stress"
                value={stress}
                onChange={handleChange}/>
                </div>
                <div className="btn_container">
                  <button className="btn" disabled={loading}>
                    {loading ? "Aan het toevoegen ..." : "toevoegen"}
                  </button>
                </div>
                
              </form>
            </div>
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
          <form onSubmit={handleSubmit}>
          <select onChange={handleDropdownChange}>
            <option value="0">Kies filter</option>
            <option value="1">1</option>
            <option value="2">2</option>
            <option value="3">3</option>
            <option value="4">4</option>
          </select>
            <div>
              <button className="btn">Fiter</button>
            </div>
          </form>
         

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

export default Dagboekje_get;
