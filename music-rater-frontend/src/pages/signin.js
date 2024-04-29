
import "./signin.css"
import React, { useState, useEffect } from 'react';
const API_DIR = "http://localhost:8080/";
export default function Signin() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');

  const handleSignIn = async (e) => {
      e.preventDefault();
      try {
        const response = axios.post(API_DIR + 'api/auth/signin', {"username": username, "password": password });
        console.log(response)
        const { token, username, roles } = response.data;
        
        localStorage.setItem('token', token);
        console.log('Sign In Successful:', response.data);
      } catch (error) {
        setError(error.response.data.message);
        console.error('Sign In Error:', error.response.data.message);
      }
    };
    return (
      <div>
        <h1>Sign in</h1>
        <form onSubmit={handleSignIn}>
          <div>
            <label>Username:</label>
            <input type="text" value={username} onChange={(e) => setUsername(e.target.value)} />
          </div>
          <div>
            <label>Password:</label>
            <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} />
          </div>
          <button type="submit">Sign In</button>
        </form>
      </div>
    );

  }

// export default function SignIn() {
//     const [username, setUsername] = useState('');
//     const [password, setPassword] = useState('');
//     const [error, setError] = useState('');

//     const handleSignIn = async (e) => {
//         e.preventDefault();
//         try {
//           const response = await axios.post('/api/auth/signin', { username, password });
//           const { token, username, roles } = response.data;
//           localStorage.setItem('token', token);
//           console.log('Sign In Successful:', response.data);
//         } catch (error) {
//           setError(error.response.data.message);
//           console.error('Sign In Error:', error.response.data.message);
//         }
//       };
  
//       return (
//         <div>
//           <h1>Sign in</h1>
//           <form onSubmit={handleSignIn}>
//             <div>
//               <label>Username:</label>
//               <input type="text" value={username} onChange={(e) => setUsername(e.target.value)} />
//             </div>
//             <div>
//               <label>Password:</label>
//               <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} />
//             </div>
//             <button type="submit">Sign In</button>
//           </form>
//         </div>
//       );
//     }

