import React from "react";
import Attachment from "./svg/Attachment";

const MessageForm = ({ handleSubmit, text, setText, setImg, loading }) => {
  return (
    <form className="message_form" onSubmit={handleSubmit}>
      <label htmlFor="img">
        <Attachment />
      </label>
      <input
        onChange={(e) => setImg(e.target.files[0])}
        type="file"
        id="img"
        accept="image/*"
        style={{ display: "none" }}
      />
      <div>
        <input
          type="text"
          placeholder="Typ hier uw bericht"
          value={text}
          onChange={(e) => setText(e.target.value)}
        />
      </div>
      <div>
      
      <button className="btn" disabled={loading}>
                        {loading ? "Aan het verzenden..." : "Verzend"}
                    </button>
                    </div>
    </form>
  );
};

export default MessageForm;
