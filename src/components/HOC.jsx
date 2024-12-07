import React from 'react';
import { Navigate } from 'react-router-dom';
import Cookies from 'js-cookie';

const ProtectedRoute = ({ element, ...rest }) => {
  // Kiểm tra nếu có token (nghĩa là người dùng đã đăng nhập)
  const isAuthenticated = Cookies.get('token');
  
  if (!isAuthenticated) {
    // Nếu người dùng chưa đăng nhập, chuyển hướng họ về trang login
    return <Navigate to="/login" />;
  }

  // Nếu người dùng đã đăng nhập, hiển thị element tương ứng
  return element;
};

export default ProtectedRoute;
