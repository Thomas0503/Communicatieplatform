import format from "date-fns/format";
import getDay from "date-fns/getDay";
import parse from "date-fns/parse";
import startOfWeek from "date-fns/startOfWeek";
import React, { useState, useEffect } from "react";
import { Calendar, dateFnsLocalizer, momentLocalizer } from "react-big-calendar";
import "react-big-calendar/lib/css/react-big-calendar.css";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import moment from 'moment'
import 'moment/locale/nl';
import { db, auth, storage } from "../firebase";


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
  import Popup from 'reactjs-popup';
import 'reactjs-popup/dist/index.css';



const Cal2 = () => {
    const user = auth.currentUser.uid;

    
    

        moment.locales('nl');
        const localizer = momentLocalizer(moment)
    
        
   
    
    
    
    const events = [
        
        
    ];

    const [newEvent, setNewEvent] = useState({ title: "", start: "", end: "" });
    const [delEvent, setDelEvent] = useState({ title: "", start: "", end: "" });

    const [date, setDate] = useState({ year: "", month: "", day: "", hour: "", minute:"" });
    const [allEvents, setAllEvents] = useState(events);
    const [text, setText] = useState("text");
    const [Dclicked, setClicked] = useState("");

    function handleAddEvent() {
        
        addDoc(collection(db, "calenderT5"), {
            title: newEvent.title,
            start: newEvent.start,
            end: newEvent.end,
            uid: user
          });
          getEv();
    }

    const getEv = async() => {
        let events = [];

        // create query object
        // execute query
        
    const calenderRef = collection(db, "calenderT5");
      // create query object
      const q = query(calenderRef, where("uid", "==", user));
      // execute query
      const unsub = onSnapshot(q, (querySnapshot) => {
        
        querySnapshot.forEach((doc) => {
        
          events.push({"title": doc.data().title, "start": doc.data().start.toDate(), "end": doc.data().end.toDate()})
          
        })
        setAllEvents(events)
        
        });
        return () => unsub();
    }

    const handleDelEvent = async() => {
      const calenderRef = collection(db, "calenderT5");
      // create query object
    //   filte gaat nu over alle userse
      const q = query(calenderRef, where("title", "==", delEvent.title));
      const unsub = onSnapshot(q, (querySnapshot) => {
        
        querySnapshot.forEach((doc1) => {
          console.log(toString(doc1.ref))
          console.log(doc1.data())
          //deleteDoc(doc(db, "calenderT5", doc.ref));
          console.log("deleted")
        })
        });
        return () => unsub();
    }
   
    useEffect(() => {
        getEv();
      }, []);

      const clicked = async() => {
        console.log("doubld")
        
      }
    return (
        <div className="App">
        
        <div>
        <h2>Add New Event</h2>

        
        <div>
            <input type="text" placeholder="Add Title" style={{ width: "20%", marginRight: "10px" }} value={newEvent.title} onChange={(e) => setNewEvent({ ...newEvent, title: e.target.value })} />
            
<DatePicker showTimeSelect placeholderText="Start Date" style={{ marginRight: "10px" }} selected={newEvent.start} onChange={(start) => setNewEvent({ ...newEvent, start })} />
            <DatePicker showTimeSelect placeholderText="End Date" selected={newEvent.end} onChange={(end) => setNewEvent({ ...newEvent, end })} />
            <button stlye={{ marginTop: "10px" }} onClick={handleAddEvent}>
                Add Event
            </button>
        </div>
        </div>
        <div>
        <h2>Delete Event</h2>
        <input type="text" placeholder="Geef Titel" style={{ width: "20%", marginRight: "10px" }} value={delEvent.title} onChange={(e) => setDelEvent({ ...delEvent, title: e.target.value })} />
        <button stlye={{ marginTop: "10px" }} onClick={handleDelEvent}>
                Del Event
            </button>
        </div>
        <Calendar localizer={localizer} events={allEvents} onDoubleClickEvent={clicked} startAccessor="start" endAccessor="end" style={{ height: 500, margin: "50px" }} />
    </div>
    )
  }

  export default Cal2;
