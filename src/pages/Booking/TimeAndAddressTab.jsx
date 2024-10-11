import React from "react";

const TimeAndAddressTab = ({ onTabChange }) => {
  return (
    <div className="col-span-1 md:col-span-2">
      <h2 className="text-xl font-semibold mt-6 text-blue-800">
        Thời gian &amp; địa điểm
      </h2>
      <div className="mt-4">
        <label className="block pb-0.5">Chọn ngày</label>
        <input
          type="date"
          className="w-full p-2 rounded-lg border-2 border-solid border-[rgb(187,215,253)]"
        />
      </div>
      <div className="mt-4">
        <label className="block pb-0.5">Tỉnh/Thành phố</label>
        <select className="w-full p-2 rounded-lg border-2 border-solid border-[rgb(187,215,253)]">
          <option>Hồ Chí Minh</option>
        </select>
      </div>
      <div className="mt-4">
        <label className="block pb-0.5">Địa điểm</label>
        <select className="w-full p-2 rounded-lg border-2 border-solid border-[rgb(187,215,253)]">
          <option>
            Hiến máu - Trường Đại học Công nghệ thành phố Hồ Chí Minh (Khu A, B)
            (thời gian làm việc từ 7g đến 11g)
          </option>
          <option>
            Hiến máu - Trường Đại học Công nghệ thành phố Hồ Chí Minh (Khu A, B)
            (thời gian làm việc từ 13g đến 17g)
          </option>
        </select>
      </div>
      <div className="mt-4">
        <label className="block pb-0.5">Nhóm máu cần hiến</label>
        <div className="flex space-x-2 mt-2">
          <button className="bg-cyan-500 text-white px-4 py-2 rounded-[10px]">
            Nhóm máu A
          </button>
          <button className="bg-yellow-500 text-white px-4 py-2 rounded-[10px]">
            Nhóm máu B
          </button>
          <button className="bg-red-500 text-white px-4 py-2 rounded-[10px]">
            Nhóm máu AB
          </button>
          <button className="bg-green-500 text-white px-4 py-2 rounded-[10px]">
            Nhóm máu O
          </button>
        </div>
      </div>
      <div className="mt-4">
        <label className="block text-gray-700 block pb-0.5">
          Chọn khung giờ bạn sẽ đến hiến máu
        </label>
        <select className="w-full p-2 rounded-lg border-2 border-solid border-[rgb(187,215,253)]">
          <option>07:00 - 11:00</option>
          <option>13:00 - 17:00</option>
        </select>
      </div>
      <div className="flex justify-end mt-6">
        <button
          onClick={() => onTabChange("FORM")}
          className="bg-blue-500 text-white px-6 py-2 rounded-lg"
        >
          Tiếp tục
        </button>
      </div>
    </div>
  );
};

export default TimeAndAddressTab;
