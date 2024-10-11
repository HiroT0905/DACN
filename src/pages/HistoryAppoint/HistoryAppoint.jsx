import React from 'react';

function HistoryAppoint() {
  return (
    <div className="min-h-screen p-4 rounded-lg p-6 max-w-4xl mx-auto">
      <h2 className="text-lg font-semibold text-blue-800">Lịch sử đặt hẹn</h2>
      <div className="mt-4 space-y-4">
        <div className="flex items-start p-4 bg-zinc-100 rounded-lg shadow-sm">
          <img
            src="/assets/img/blood.png"
            alt="blood drop icon"
            className="w-14 h-14 mr-4"
          />
          <div className="flex-1">
            <h3 className="text-blue-800 font-semibold">
              Trường Đại học Công nghệ thành phố Hồ Chí Minh (Khu E)
            </h3>
            <p className="text-zinc-600">
              <span className="inline-block mr-2">
                <img
                  src="/assets/img/local222.png"
                  alt="location icon"
                  className="inline w-4 h-4 text-[black] mr-1"
                />
                Khu Công nghệ cao Xa Lộ Hà Nội, Hiệp Phú, Thủ Đức, Thành phố Hồ Chí Minh, Việt Nam
              </span>
              <span className="inline-block">
                <img
                  src="/assets/img/alarm.png"
                  alt="clock icon"
                  className="inline w-4 h-4 mr-1"
                />
                07:00 đến 11:00 - 30/06/2024
              </span>
            </p>
          </div>
          <div className="flex flex-col items-end">
            <button className="bg-red-500 text-white px-3 py-1 rounded-full mb-2">
              Đã xoá
            </button>
            <a href="#" className="text-blue-600">Xem chi tiết</a>
          </div>
        </div>
        <div className="flex items-start p-4 bg-zinc-100 rounded-lg shadow-sm">
          <img
            src="/assets/img/blood.png"
            alt="blood drop icon"
            className="w-14 h-14 mr-4"
          />
          <div className="flex-1">
            <h3 className="text-blue-800 font-semibold">
              Trường Đại học Công nghệ thành phố Hồ Chí Minh (Khu A, B)
            </h3>
            <p className="text-zinc-600">
              <span className="inline-block mr-2">
                <img
                  src="/assets/img/local222.png"
                  alt="location icon"
                  className="inline w-4 h-4 mr-1"
                />
                475A Đ. Điện Biên Phủ, Phường 25, Bình Thạnh, Thành phố Hồ Chí Minh, Việt Nam
              </span>
              <br />
              <span className="inline-block">
                <img
                  src="/assets/img/alarm.png"
                  alt="clock icon"
                  className="inline w-4 h-4 mr-1"
                />
                07:00 đến 11:00 - 05/07/2024
              </span>
            </p>
          </div>
          <div className="flex flex-col items-end">
            <button className="bg-red-500 text-white px-3 py-1 rounded-full mb-2">
              Đã xoá
            </button>
            <a href="#" className="text-blue-600">Xem chi tiết</a>
          </div>
        </div>
      </div>
    </div>
  );
}

export default HistoryAppoint;