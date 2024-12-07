import React from 'react';

function Contact() {
  return (
    <div className="bg-white rounded-lg shadow-md p-6 max-w-4xl mx-auto my-8">
      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
        {/* Left Section - Contact Information */}
        <div className="bg-blue-500 text-white p-8 rounded-[30px_30px_30px_0px]">
          <h2 className="text-3xl font-bold mb-6 text-center">Liên hệ</h2>
        
          {/* Email Section */}
          <div className="flex items-center mb-6">
            <img src="./assets/img/email.svg" alt="Email Icon" className="mr-4 w-8 h-8" />
            <span className="font-medium text-xl">Email</span>
          </div>
        
          <p className="text-base mb-6">
            {/* <a href="mailto:sonmaket0201@gmail.com" target="_blank">sonmaket0201@gmail.com</a> */}
          </p>
        
          {/* Divider */}
          <hr className="border-white opacity-50 my-6" />
        
          {/* Hotline Section */}
          <div className="flex items-center mb-6">
            <img src="./assets/img/phone111111.svg" alt="Phone Icon" className="mr-4 w-8 h-8" />
            <p className="font-medium text-xl">Hotline</p>
          </div>
        
          {/* Hotline Information */}
          <div className="text-base leading-relaxed">
            <p className="mb-4">Khu AB:<br/>0706389781</p>
            <p className="mb-4">Khu E:<br/>0961323076</p>
          </div>
        </div>
        
    
        {/* Right Section - Contact Form */}
        <div className="p-6 bg-white">
          <h2 className="text-2xl font-semibold mb-4 text-blue-700">Gửi lời nhắn cho chúng tôi</h2>
          <p className="mb-4 text-gray-600 text-sm">
            Nếu bạn có bất kỳ thắc mắc nào liên quan đến các hoạt động hiến máu tình nguyện, xin vui lòng liên hệ với chúng tôi qua địa chỉ email
          {/* <a href="mailto:sonmaket0201@gmail.com" target="_blank" className="text-blue-600 ml-1">sonmaket0201@gmail.com</a> hoặc gửi thông tin cho chúng tôi theo mẫu bên dưới: */}
          </p>
    
          <form>
            <div className="mb-4">
              <label htmlFor="name" className="block text-sm text-gray-700">Họ và tên</label>
              <input type="text" id="name" placeholder="Vui lòng nhập họ và tên" className="w-full p-2 border border-gray-300 rounded text-sm" />
            </div>
            <div className="mb-4">
              <label htmlFor="email" className="block text-sm text-gray-700">Email</label>
              <input type="email" id="email" placeholder="Vui lòng nhập email" className="w-full p-2 border border-gray-300 rounded text-sm" />
            </div>
            <div className="mb-4">
              <label htmlFor="message" className="block text-sm text-gray-700">Lời nhắn</label>
              <textarea id="message" placeholder="Vui lòng nhập lời nhắn" className="resize-none w-full p-2 border border-gray-300 rounded text-sm"></textarea>
            </div>
            <div className="flex justify-center">
              <button type="submit" className="bg-blue-500 text-white py-2 px-4 rounded text-sm">Gửi lời nhắn</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
}

export default Contact;