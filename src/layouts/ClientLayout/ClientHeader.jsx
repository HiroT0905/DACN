import React, { useState, useEffect, useRef } from "react";
import { Link, NavLink, useLocation } from "react-router-dom";
import { ROUTE_PATH } from "../../constants/routes";

const ClientHeader = () => {
  const [showDropdown, setShowDropdown] = useState(false);
  const dropdownRef = useRef(null); // Tạo ref cho dropdown
  const location = useLocation(); // Sử dụng useLocation để theo dõi thay đổi location

  const handleToggleDropdown = () => {
    setShowDropdown(!showDropdown);
  };

  // Hàm để đóng dropdown khi bấm ra ngoài
  const handleClickOutside = (event) => {
    if (dropdownRef.current && !dropdownRef.current.contains(event.target)) {
      setShowDropdown(false);
    }
  };

  useEffect(() => {
    // Thêm sự kiện click khi component được mount
    document.addEventListener("mousedown", handleClickOutside);
    return () => {
      // Dọn dẹp sự kiện khi component unmount
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, []);

  useEffect(() => {
    // Đóng dropdown khi location thay đổi
    setShowDropdown(false);
  }, [location]); // Theo dõi thay đổi của location

  return (
    <>
      <div className="header-container w-full h-[57px] flex justify-center items-center text-black">
        <div className="header-content w-[1024px] h-full flex justify-between items-center">
          <div className="languages">
            <Link to="" className="text-[16px] text-[var(--black)] font-bold mr-[1px]">
              VN
            </Link>
            |
            <Link to="" className="text-[16px] text-[var(--gray)] font-bold ml-[1px]">
              EN
            </Link>
          </div>
          <div className="flex items-center space-x-4">
            <Link to={ROUTE_PATH.HOME}>
              <img
                src="/assets/img/logo-hutech.png"
                alt="Logo 1"
                className="h-8"
              />
            </Link>
          </div>

          <div className="relative inline-block text-left" ref={dropdownRef}>
            <div>
              <button
                onClick={handleToggleDropdown}
                className="justify-center w-full rounded-md border border-gray-300 shadow-sm px-4 py-2 bg-white text-sm font-medium text-gray-700 hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
              >
                Tài khoản
              </button>
            </div>
            <div
              id="dropdown"
              className={`origin-top-right absolute right-0 mt-2 w-56 rounded-md shadow-lg bg-white ring-1 ring-black ring-opacity-5 z-50 ${
                showDropdown ? "" : "hidden"
              }`}
            >
              <div
                className="py-1"
                role="menu"
                aria-orientation="vertical"
                aria-labelledby="options-menu"
              >
                <Link to="/Profile">
                  <a
                    className="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100"
                    role="menuitem"
                  >
                    Thông tin cá nhân
                  </a>
                </Link>

                <Link to="/settings">
                  <a
                    className="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100"
                    role="menuitem"
                  >
                    Thiết lập cá nhân
                  </a>
                </Link>

                <Link to="/logout">
                  <a
                    className="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100"
                    role="menuitem"
                  >
                    Đăng xuất tài khoản
                  </a>
                </Link>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div className="navigate-container w-full h-[42px] bg-[var(--blue-dark)] flex justify-center items-center text-black">
        <div className="navigate-content w-[1024px] h-full flex justify-center items-center">
          <div
            className="info flex justify-center items-center gap-[50px] text-white"
            id="navigation"
          >
            <NavLink
              to={ROUTE_PATH.HOME}
              className="py-[7px] text-[16px] text-white"
            >
              TRANG CHỦ
            </NavLink>

            <NavLink
              to={ROUTE_PATH.APPOINTMENTS}
              className="py-[7px] text-[16px] text-white"
            >
              LỊCH HẸN CỦA BẠN
            </NavLink>

            <NavLink
              to={ROUTE_PATH.HISTORYAPPOINT}
              className="py-[7px] text-[16px] text-white"
            >
              LỊCH SỬ ĐẶT HẸN
            </NavLink>

            <NavLink
              to={ROUTE_PATH.CERTIFICATE}
              className="py-[7px] text-[16px] text-white"
            >
              CHỨNG NHẬN
            </NavLink>

            <NavLink
              to={ROUTE_PATH.FAQ}
              className="py-[7px] text-[16px] text-white"
            >
              HỎI - ĐÁP
            </NavLink>

            <NavLink
              to={ROUTE_PATH.NEWS}
              className="py-[7px] text-[16px] text-white"
            >
              TIN TỨC
            </NavLink>

            <NavLink
              to={ROUTE_PATH.CONTACT}
              className="py-[7px] text-[16px] text-white"
            >
              LIÊN HỆ
            </NavLink>
          </div>
        </div>
      </div>
    </>
  );
};

export default ClientHeader;
