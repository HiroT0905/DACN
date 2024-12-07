import React from "react";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";

// import { createBrowserRouter, RouterProvider } from "react-router-dom";
// import { ROUTE_PATH } from "./constants/routes";
import Home from "./pages/Home/Home";
// import ClientLayout from "./layouts/ClientLayout/ClientLayout";
import Appointments from "./pages/Appointments/Appointments";
import Faq from "./pages/FAQ/Faq";
import Booking from "./pages/Booking/Booking";
import Certificate from "./pages/Certificate/certificate";
import HistoryAppoint from "./pages/HistoryAppoint/HistoryAppoint";
import News from "./pages/News/News";
import Contact from "./pages/Contact/Contact";
import Settings from "./pages/Account/Settings";
import Profile from "./pages/Account/Profile";
import UserService from './service/userService'
import RegistrationPage from './pages/auth/RegistrationPage.jsx'
import ClientFooter from "./layouts/ClientLayout/ClientFooter.jsx";

import LoginPage from './pages/auth/LoginPage';
import ClientHeader from "./layouts/ClientLayout/ClientHeader.jsx";
// import RegistrationPage from './pages/auth/RegistrationPage';
// const App = () => {
//   const router = createBrowserRouter([
//     {
//       path: ROUTE_PATH.HOME,
//       element: <ClientLayout />,
//       children: [
//         {
//           path: ROUTE_PATH.HOME,
//           element: <Home />,
//         },
//         {
//           path: ROUTE_PATH.APPOINTMENTS,
//           element: <Appointments />,
//         },
//         {
//           path: ROUTE_PATH.BOOKING,
//           element: <Booking />,
//         },
//         {
//           path: ROUTE_PATH.FAQ,
//           element: <Faq />,
//         },
//         {
//           path: ROUTE_PATH.CERTIFICATE,
//           element: <Certificate />,
//         },
//         {
//           path: ROUTE_PATH.HISTORYAPPOINT,
//           element: <HistoryAppoint />,
//         },
//         {
//           path: ROUTE_PATH.NEWS,
//           element: <News />,
//         },
//         {
//           path: ROUTE_PATH.CONTACT,
//           element: <Contact />,
//         },
//         {
//           path: ROUTE_PATH.SETTINGS,
//           element: <Settings />,
//         },
//         {
//           path: ROUTE_PATH.PROFILE,
//           element: <Profile />,
//         },
//         {
//           path: ROUTE_PATH.LOGOUT,
//           element: <logout />,
//         },
//         {
//           path:ROUTE_PATH.LOGIN,
//           element: <LoginPage/>,

//         }
//       ],
//     },
//   ]);

//   return <RouterProvider router={router} />;
// };


import ProtectedRoute from './components/HOC.jsx';  // Import HOC



function App() {

  return (
    <BrowserRouter>
      <div className="App">
        <ClientHeader />
        <div className="content">
          <Routes>
            <Route exact path="/" element={<Home />} />
            <Route exact path="/login" element={<LoginPage />} />
            <Route exact path="/register" element={<RegistrationPage  />} />
            <Route exact path="/certificate" element={<Certificate  />} />
            <Route exact path="/faq" element={<Faq  />} />
            <Route exact path="/news" element={<News   />} />
            <Route exact path="/contact" element={<Contact   />} />

            {/* Các route yêu cầu đăng nhập */}
          
            <Route
              path="/appointments"
              element={<ProtectedRoute element={<Appointments />} />}
            />
            <Route
              path="/profile"
              element={<ProtectedRoute element={<Profile />} />}
            />
             <Route
              path="/history"
              element={<ProtectedRoute element={<HistoryAppoint />} />}
            />
           
      
            <Route path="*" element={<Navigate to="/login" />} />‰
          </Routes>
        </div>
        <ClientFooter />
      </div>
    </BrowserRouter>
  );
}

export default App;
