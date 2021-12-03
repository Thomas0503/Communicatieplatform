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



const CalT = () => {
  const user = auth.currentUser.uid;





  moment.locales('nl');
  const localizer = momentLocalizer(moment)






  var events = [];

  const [newEvent, setNewEvent] = useState({ title: "", start: "", end: "", locatie: "", opmerkingen: "" });
  const [delEvent, setDelEvent] = useState({ title: "", start: "", end: "" });

  const [date, setDate] = useState({ year: "", month: "", day: "", hour: "", minute: "" });
  const [allEvents, setAllEvents] = useState(events);
  const [text, setText] = useState("text");
  const [Dclicked, setClicked] = useState("");
  const [selected, setSelected] = useState();
  const [tekst, setTekst] = useState("");
  const [eventTitle, setEventTitle] = useState("leeg");
  const [filter, setState] = useState("-1");
  const [filterkal, setStateKal] = useState("EqI10LALkGOjjonWT9LGSUIdc572");
  const [gezinnen, setGezinnen] = useState([]);
  const [kalView, setkalView] = useState("null"); // set default op default ddropdown
  const [displayed, setDisplayed] = useState();


  const namesHard = { "EqI10LALkGOjjonWT9LGSUIdc572": "andee pc", "wVEKX0xL6xRiSvztD1EFV3gHos12": "tt" }




  const handleGez = async () => {

    let gezinnen1 = [];

    // create query object
    // execute query

    const usersRef = collection(db, "users");
    // create query object
    const q = query(usersRef, where("uid", "==", user));
    // execute query
    const unsub = onSnapshot(q, (querySnapshot) => {

      querySnapshot.forEach((doc) => {
        gezinnen1 = doc.data().pleeggezinnen;

      })
      setGezinnen(gezinnen1);

    });
    return () => unsub();

  }
  

  const handleSelected = (event) => {
    if (displayed != event) {
      setDisplayed(event);
      setTekst(
        <>
          <div>locatie: {event.locatie}</div>
          <div>opmerkingen: {event.opmerkingen}</div>

          <button onClick={handleDelEvent}> verwijder {event.title} </button>
        </>
      );
      console.log(event.title);
      setEventTitle(event.title);
    } else {
      setSelected();
      setDisplayed();
      setTekst();
    }

  }

  function handleAddEvent() {
    var c = [filter]
    if (filter == "-1") {
      c = gezinnen
    }
    c.map((object, i) => {

      addDoc(collection(db, "calenderT", "calenderT", object), {
        title: newEvent.title,
        start: newEvent.start,
        end: newEvent.end,
        opmerkingen: newEvent.opmerkingen,
        locatie: newEvent.locatie

      });
    })


    getEv2();
    setNewEvent({ title: "", start: "", end: "", locatie: "", opmerkingen: "" })
  }


  const getEv2 = async () => {
    setAllEvents([]);
    let events1 = [];
    // create query object
    // execute query

    const calenderRef = collection(db, "calenderT", "calenderT", kalView);
    // create query object
    const q = query(calenderRef);
    // execute query
    const unsub = onSnapshot(q, (querySnapshot) => {

      querySnapshot.forEach((doc) => {

        events1.push({ "title": doc.data().title, "start": doc.data().start.toDate(), "end": doc.data().end.toDate(), "opmerkingen": doc.data().opmerkingen, "locatie": doc.data().locatie })
      })
      setAllEvents(events1)
      //const events2 = events
    });
    return () => unsub();
  }

  const handleDelEvent = async () => {
    const calenderRef = collection(db, "calenderT", "calenderT", kalView);
    // create query object
    console.log(eventTitle)
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
        deleteDoc(doc(db, "calenderT", "calenderT", kalView, doc1.ref._key.path.segments[8]));
        console.log("deleted");

      })
      setTekst("");
      getEv2();
    });

    return () => unsub();
  }

  useEffect(() => {
    localStorage.setItem("kalU", "def")
    //const storedSelectedOption = parseInt(sessionStorage.getItem('kalView'))
    //setkalView(storedSelectedOption)
    handleGez();
    setAllEvents([]);
    getEv2();
  }, []);

  const clicked = (event) => {
    setSelected(event);
    setEventTitle(event.title);

  }
  const handleDropdownChange = (e) => {
    setState(e.target.value);
    console.log(filter)
  };

  const handleDropdownChange2 = (e) => {
    setkalView(e.target.value);
    //sessionStorage.setItem("KalU", "sesssiiiee")
    //console.log(sessionStorage.getItem("kalU"))
    //console.log(filter)
  };

  const handleCalFil = async () => {
    //window.sessionStorage.setItem(kalView, kalView);
    console.log(kalView)
    getEv2()
  };





  return (
    <div className="App">

      <div>
        <h2>Voeg een niewe afspraak toe</h2>


        <div>
          <input type="text" placeholder="Add Title" style={{ width: "20%", marginRight: "10px" }} value={newEvent.title} onChange={(e) => setNewEvent({ ...newEvent, title: e.target.value })} />
          <input type="text" placeholder="Locatie" style={{ width: "20%", marginRight: "10px" }} value={newEvent.locatie} onChange={(e) => setNewEvent({ ...newEvent, locatie: e.target.value })} />
          <input type="text" placeholder="Opmerkingen" style={{ width: "20%", marginRight: "10px" }} value={newEvent.opmerkingen} onChange={(e) => setNewEvent({ ...newEvent, opmerkingen: e.target.value })} />

          <DatePicker showTimeSelect placeholderText="Start Date" style={{ marginRight: "10px" }} selected={newEvent.start} onChange={(start) => setNewEvent({ ...newEvent, start })} />
          <DatePicker showTimeSelect placeholderText="End Date" selected={newEvent.end} onChange={(end) => setNewEvent({ ...newEvent, end })} />

          <select onChange={handleDropdownChange}>
            {gezinnen.map((object, i) =>
              <option value={object}>{namesHard[object]}</option>

            )}
            <option value="-1">Iedereen</option>

          </select>

              <br/>
          <button  className="btn" stlye={{ marginTop: "10px" }} onClick={handleAddEvent}>
            Voeg afspraak toe
          </button>
        </div>
        <br/>
        <br/>

        Bekijk kalender van:
        <select onChange={handleDropdownChange2}>
          {gezinnen.map((object, i) =>
            <option value={object}>{namesHard[object]}</option>

          )}

        </select>



        <div>
          <button className="btn" onClick={handleCalFil}>
            Bekijk Kalender
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
      {sessionStorage.getItem("kalU")}
      {kalView}
    </div>
  )
}

export default CalT;
