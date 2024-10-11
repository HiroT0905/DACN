import React from "react";
import { Link } from "react-router-dom";
import { ROUTE_PATH } from "../../constants/routes";

const Appointments = () => {
  return (
    <div className="bg-zinc-100 min-h-screen p-4">
      <div className="bg-white rounded-lg shadow-md my-2 p-6 max-w-4xl mx-auto">
        <h2 className="text-xl font-semibold mb-4 text-blue-800">
          Thông tin đăng ký hiến máu
        </h2>
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div className="bg-zinc-50 rounded-lg p-4">
            <h3 className="text-lg font-semibold mb-2 text-blue-800">
              Thông tin cá nhân
            </h3>
            <div className="space-y-2">
              <div className="flex justify-between">
                <span className="font-medium">Họ và tên:</span>
                <span>-</span>
              </div>
              <div className="flex justify-between">
                <span className="font-medium">Số CMND:</span>
                <span>-</span>
              </div>
              <div className="flex justify-between">
                <span className="font-medium">Ngày sinh:</span>
                <span>-</span>
              </div>
              <div className="flex justify-between">
                <span className="font-medium">Giới tính:</span>
                <span>-</span>
              </div>
              <div className="flex justify-between">
                <span className="font-medium">Nghề nghiệp:</span>
                <span>-</span>
              </div>
              <div className="flex justify-between">
                <span className="font-medium">Đơn vị:</span>
                <span>-</span>
              </div>
              <div className="flex justify-between">
                <span className="font-medium">Nhóm máu:</span>
                <span>-</span>
              </div>
            </div>
          </div>
          <div className="bg-zinc-50 rounded-lg p-4">
            <h3 className="text-lg font-semibold mb-2 text-blue-800">
              Thông tin liên hệ
            </h3>
            <div className="space-y-2">
              <div className="flex justify-between">
                <span className="font-medium">Địa chỉ liên lạc:</span>
                <span>-</span>
              </div>
              <div className="flex justify-between">
                <span className="font-medium">Điện thoại di động:</span>
                <span>-</span>
              </div>
              <div className="flex justify-between">
                <span className="font-medium">Điện thoại bàn:</span>
                <span>-</span>
              </div>
              <div className="flex justify-between">
                <span className="font-medium">Email:</span>
                <span>-</span>
              </div>
            </div>
          </div>
        </div>
        <div className="bg-zinc-50 rounded-lg p-4 mt-4">
          <h3 className="text-lg font-semibold mb-2 text-blue-800">
            Phiếu đăng ký hiến máu
          </h3>
          <div className="flex flex-col items-center justify-center h-full">
            <img
              src="/assets/img/phieuDangky.png"
              alt="Document Icon"
              className="mb-4 w-[140px]"
            />
            <p className="text-zinc-500">Chưa có phiếu đăng ký hiến máu</p>
          </div>
        </div>
        <div className="flex justify-center mt-6">
          <Link to={ROUTE_PATH.BOOKING}>
            <button className="bg-blue-500 text-white px-4 py-2 rounded-lg">
              Đăng ký hiến máu
            </button>
          </Link>
        </div>
      </div>
    </div>
  );
};

export default Appointments;
