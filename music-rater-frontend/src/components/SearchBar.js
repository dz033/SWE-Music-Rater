import React, {useState} from "react";

import {FaSearch} from "react-icons/fa";

export const SearchBar = () =>{
    const [input, setInput] = useState("");

    const fetchData = (value) => {
        fetch("https://jsonplaceholder.typicode.com/users")
        .then((response) => {response.json
        console.log(response)})
        .then(json => {
            //console.log(json);
            const results = json.filter((user) => {
                return user && user.name.toLowerCase().includes(value)
            })
            console.log(results)
        });
    
    }

    const handleChange = (value) => {
        setInput(value)
        //fetchData(value)
    }
    return (
        <div className="input-wrapper">
            <FaSearch id="search-icon"/>
            <input placeholder="Type to search..." value={input} onChange={(e) => handleChange(e.target.value)}/>
        </div>
    );
}