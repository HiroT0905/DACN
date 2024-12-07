import React from "react";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import { ROUTE_PATH } from "./constants/routes";
import Home from "./pages/Home/Home";
import ClientLayout from "./layouts/ClientLayout/ClientLayout";
import Appointments from "./pages/Appointments/Appointments";
import Faq from "./pages/FAQ/Faq";
import Booking from "./pages/Booking/Booking";
import Certificate from "./pages/Certificate/certificate";
import HistoryAppoint from "./pages/HistoryAppoint/HistoryAppoint";
import News from "./pages/News/News";
import Contact from "./pages/Contact/Contact";
import Account from "./pages/Account/Settings"
import Settings from "./pages/Account/Settings";
import Profile from "./pages/Account/Profile";

const App = () => {
  const router = createBrowserRouter([
    {
      path: ROUTE_PATH.HOME,
      element: <ClientLayout />,
      children: [
        {
          path: ROUTE_PATH.HOME,
          element: <Home />,
        },
        {
          path: ROUTE_PATH.APPOINTMENTS,
          element: <Appointments />,
        },
        {
          path: ROUTE_PATH.BOOKING,
          element: <Booking />,
        },
        {
          path: ROUTE_PATH.FAQ,
          element: <Faq />,
        },
        {
          path: ROUTE_PATH.CERTIFICATE,
          element: <Certificate />,
        },
        {
          path: ROUTE_PATH.HISTORYAPPOINT,
          element: <HistoryAppoint />,
        },
        {
          path: ROUTE_PATH.NEWS,
          element: <News />,
        },
        {
          path: ROUTE_PATH.CONTACT,
          element: <Contact />,
        },
        {
          path: ROUTE_PATH.SETTINGS,
          element: <Settings />,
        },
        {
          path: ROUTE_PATH.PROFILE,
          element: <Profile />,
        },
        {
          path: ROUTE_PATH.LOGOUT,
          element: <logout />,
        },
      ],
    },
  ]);

  return <RouterProvider router={router} />;
};

export default App;
