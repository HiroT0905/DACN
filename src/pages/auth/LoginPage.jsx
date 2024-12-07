// import React, { useState } from "react";
// import { useNavigate } from "react-router-dom";
// import UserService from '../../service/userService';
// import Cookies from 'js-cookie'; // Thêm thư viện cookies

// function LoginPage() {
//   const [cccd, setCccd] = useState('');
//   const [password, setPassword] = useState('');
//   const [error, setError] = useState('');
//   const navigate = useNavigate();

//   const handleSubmit = async (e) => {
//     e.preventDefault();

//     try {
//       // Gửi request login để lấy thông tin người dùng
//       const userData = await UserService.login(cccd, password);
//       console.log(userData);

//       if (userData.token) {
//         // Lưu thông tin vào cookies (token, role, userName)
//         Cookies.set('token', userData.token, { expires: 1, secure: true, sameSite: 'Strict' });
//         Cookies.set('role', userData.role, { expires: 1, secure: true, sameSite: 'Strict' });
//         Cookies.set('userName', userData.cccd, { expires: 1, secure: true, sameSite: 'Strict' });
//         Uẻ
//         // Điều hướng người dùng tới trang chính sau khi đăng nhập thành công
//         navigate('/');
//       } else {
//         setError(userData.message); // Hiển thị thông báo lỗi nếu không có token
//       }
//     } catch (error) {
//       console.log(error);
//       setError(error.message); // Hiển thị thông báo lỗi trong trường hợp có ngoại lệ
//       setTimeout(() => {
//         setError(''); // Xóa thông báo lỗi sau 5 giây
//       }, 5000);
//     }
//   };

//   return (
//     <div className="auth-container">
//       <h2>Login</h2>
//       {error && <p className="error-message">{error}</p>}
//       <form onSubmit={handleSubmit}>
//         <div className="form-group">
//           <label>CCCD: </label>
//           <input
//             type="text"
//             value={cccd}
//             onChange={(e) => setCccd(e.target.value)}
//             required
//           />
//         </div>
//         <div className="form-group">
//           <label>Password: </label>
//           <input
//             type="password"
//             value={password}
//             onChange={(e) => setPassword(e.target.value)}
//             required
//           />
//         </div>
//         <button type="submit">Login</button>
//       </form>
//     </div>
//   );
// }

// export default LoginPage;

import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import UserService from '../../service/userService';
import Cookies from 'js-cookie'; // Thêm thư viện cookies

function LoginPage() {
  const [cccd, setCccd] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      // Gửi request login để lấy thông tin người dùng
      const userData = await UserService.login(cccd, password);
      console.log(userData);

      if (userData.token) {
        // Lưu thông tin vào cookies (token, role, userName)
        Cookies.set('token', userData.token, { expires: 1, secure: true, sameSite: 'Strict' });
        Cookies.set('role', userData.role, { expires: 1, secure: true, sameSite: 'Strict' });
        Cookies.set('userName', userData.cccd, { expires: 1, secure: true, sameSite: 'Strict' });
        
        // Điều hướng người dùng tới trang chính sau khi đăng nhập thành công
        navigate('/');
      } else {
        setError(userData.message); // Hiển thị thông báo lỗi nếu không có token
      }
    } catch (error) {
      console.log(error);
      setError(error.message); // Hiển thị thông báo lỗi trong trường hợp có ngoại lệ
      setTimeout(() => {
        setError(''); // Xóa thông báo lỗi sau 5 giây
      }, 5000);
    }
  };

  return (
    <div className="auth-container">
      <div className="login-form">
        <h2 className="text-center">Login</h2>
        {error && <p className="error-message">{error}</p>}
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="cccd">CCCD: </label>
            <input
              type="text"
              id="cccd"
              value={cccd}
              onChange={(e) => setCccd(e.target.value)}
              required
              className="input-field"
            />
          </div>
          <div className="form-group">
            <label htmlFor="password">Password: </label>
            <input
              type="password"
              id="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
              className="input-field"
            />
          </div>
          <button type="submit" className="submit-btn">Login</button>
        </form>
      </div>
    </div>
  );
}

export default LoginPage;
