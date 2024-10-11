import React from 'react';

function Profile() {
  const thongTinCaNhan = {
    hoVaTen: '-',
    soCMND: '-',
    ngaySinh: '-',
    gioiTinh: '-',
    ngheNghiep: '-',
    donVi: '-',
    nhomMau: '-',
  };

  const thongTinLienHe = {
    diaChiLienLac: '-',
    dienThoaiDiDong: '-',
    dienThoaiBan: '-',
    email: '-',
  };

  return (
    <div className="bg-zinc-100 min-h-screen p-4">
      <div className="bg-white rounded-lg shadow-md p-6 max-w-4xl mx-auto">
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
                <span>{thongTinCaNhan.hoVaTen}</span>
              </div>
              <div className="flex justify-between">
                <span className="font-medium">Số CMND:</span>
                <span>{thongTinCaNhan.soCMND}</span>
              </div>
              <div className="flex justify-between">
                <span className="font-medium">Ngày sinh:</span>
                <span>{thongTinCaNhan.ngaySinh}</span>
              </div>
              <div className="flex justify-between">
                <span className="font-medium">Giới tính:</span>
                <span>{thongTinCaNhan.gioiTinh}</span>
              </div>
              <div className="flex justify-between">
                <span className="font-medium">Nghề nghiệp:</span>
                <span>{thongTinCaNhan.ngheNghiep}</span>
              </div>
              <div className="flex justify-between">
                <span className="font-medium">Đơn vị:</span>
                <span>{thongTinCaNhan.donVi}</span>
              </div>
              <div className="flex justify-between">
                <span className="font-medium">Nhóm máu:</span>
                <span>{thongTinCaNhan.nhomMau}</span>
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
                <span>{thongTinLienHe.diaChiLienLac}</span>
              </div>
              <div className="flex justify-between">
                <span className="font-medium">Điện thoại di động:</span>
                <span>{thongTinLienHe.dienThoaiDiDong}</span>
              </div>
              <div className="flex justify-between">
                <span className="font-medium">Điện thoại bàn:</span>
                <span>{thongTinLienHe.dienThoaiBan}</span>
              </div>
              <div className="flex justify-between">
                <span className="font-medium">Email:</span>
                <span>{thongTinLienHe.email}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Profile;