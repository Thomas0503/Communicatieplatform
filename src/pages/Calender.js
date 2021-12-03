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
import { useHistory } from "react-router-dom";


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



const Calender = () => {
  const user = auth.currentUser.uid;
  const history = useHistory();



  moment.locales('nl');
  const localizer = momentLocalizer(moment)






  const events = [];

  const [newEvent, setNewEvent] = useState({ title: "", start: "", end: "", locatie: "", opmerkingen: "" });
  const [delEvent, setDelEvent] = useState({ title: "", start: "", end: "" });

  const [date, setDate] = useState({ year: "", month: "", day: "", hour: "", minute: "" });
  const [allEvents, setAllEvents] = useState(events);
  const [text, setText] = useState("text");
  const [Dclicked, setClicked] = useState("");
  const [selected, setSelected] = useState();
  const [tekst, setTekst] = useState("");
  const [eventTitle, setEventTitle] = useState("leeg");
  const [displayed, setDisplayed] = useState();

  
  const handleSelected = (event) => {
    if (displayed != event) {
      setDisplayed(event);
      setTekst(
        <>
          <div>locatie: {event.locatie}</div>
          <div>opmerkingen: {event.opmerkingen}</div>

          <button className="btn" onClick={handleDelEvent}> verwijder {event.title} </button>
        </>
      );
      console.log(event.title);
      setEventTitle(event.title);
    }else{
      setSelected();
      setDisplayed();
      setTekst();
    }
    

  }

  function handleAddEvent() {

    addDoc(collection(db, "calenderT", "calenderT", user), {
      title: newEvent.title,
      start: newEvent.start,
      end: newEvent.end,
      locatie: newEvent.locatie,
      opmerkingen: newEvent.opmerkingen
    });
    getEv();
    setNewEvent({ title: "", start: "", end: "", locatie: "", opmerkingen: "" })
  }

  const getEv = async () => {
    setAllEvents([]);
    let events = [];
    // create query object
    // execute query

    const calenderRef = collection(db, "calenderT", "calenderT", user);
    // create query object
    const q = query(calenderRef);
    // execute query
    const unsub = onSnapshot(q, (querySnapshot) => {

      querySnapshot.forEach((doc) => {

        events.push({ "title": doc.data().title, "start": doc.data().start.toDate(), "end": doc.data().end.toDate(), "opmerkingen": doc.data().opmerkingen, "locatie": doc.data().locatie })

      })
      setAllEvents(events)

    });
    return () => unsub();
  }

  const handleDelEvent = async () => {
    const calenderRef = collection(db, "calenderT", "calenderT", user);
    // create query object
    const q3 = query(calenderRef, where("title", "==", eventTitle));
    const unsub = onSnapshot(q3, (querySnapshot) => {
      //console.log(selected)
      querySnapshot.forEach((doc1) => {
        //console.log("eventTitle");
        //console.log(eventTitle);
        //console.log(event);
        //console.log(doc1.ref)
        //console.log(doc1.ref._key.path.segments[8])
        //console.log(doc1.data())
        deleteDoc(doc(db, "calenderT", "calenderT", user, doc1.ref._key.path.segments[8]));
        console.log("deleted");

      })
      setTekst("");
      getEv();
    });

    return () => unsub();
  }

  useEffect(() => {
    setAllEvents([]);
    getEv();
  }, []);

  const clicked = (event) => {
    setSelected(event);
    setEventTitle(event.title);

  }





  return (
    <div className="App">

      <div>
        <h2>Voeg een niewe afspraak toe</h2>


        <div>
          <input type="text" placeholder="Titel" style={{ width: "20%", marginRight: "10px" }} value={newEvent.title} onChange={(e) => setNewEvent({ ...newEvent, title: e.target.value })} />
          <input type="text" placeholder="Locatie" style={{ width: "20%", marginRight: "10px" }} value={newEvent.locatie} onChange={(e) => setNewEvent({ ...newEvent, locatie: e.target.value })} />
          <input type="text" placeholder="Opmerkingen" style={{ width: "20%", marginRight: "10px" }} value={newEvent.opmerkingen} onChange={(e) => setNewEvent({ ...newEvent, opmerkingen: e.target.value })} />

          <DatePicker showTimeSelect placeholderText="Start Datum" style={{ marginRight: "10px" }} selected={newEvent.start} onChange={(start) => setNewEvent({ ...newEvent, start })} format="dd-MM-yyyy" />
          <DatePicker format="yyyy-dd-MM" showTimeSelect placeholderText="Eind Datum" selected={newEvent.end} onChange={(end) => setNewEvent({ ...newEvent, end })} />
          <button className="btn" stlye={{ marginTop: "10px" }} onClick={handleAddEvent}>
            Voeg afspraak toe
          </button>
        </div>
      </div>
      <div>
        <h4>{tekst}</h4>
      </div>
      <Calendar eventPropGetter={(event) => {
    const backgroundColor = event.allday ? '#adab09' : '#adab09';
    return { style: { backgroundColor } }
  }}localizer={localizer} events={allEvents} onDoubleClickEvent={handleSelected} onSelectEvent={clicked} startAccessor="start" endAccessor="end" style={{ height: 500, margin: "50px" }} />

    </div>
  )
}

export default Calender;
