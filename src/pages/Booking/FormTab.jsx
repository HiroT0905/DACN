import React from "react";
import { Link } from 'react-router-dom';

const FormTab = ({ onTabChange }) => {
  return (
    <div className="col-span-1 md:col-span-2">
      <h2 className="text-xl font-semibold mt-6">Phiếu đăng ký hiến máu</h2>
      <form className="mt-4">
        {/* Question 1 */}
        <div className="mb-4 p-4 bg-gray-100 rounded-md">
          <label className="block font-semibold mb-2">
            1. Anh/chị đã từng hiến máu chưa?
          </label>
          <div className="flex items-center mb-2">
            <input type="radio" id="q1_yes" name="q1" className="mr-2" />
            <label htmlFor="q1_yes">Có</label>
          </div>
          <div className="flex items-center">
            <input type="radio" id="q1_no" name="q1" className="mr-2" />
            <label htmlFor="q1_no">Không</label>
          </div>
        </div>
        {/* Question 2 */}
        <div className="mb-4 p-4 bg-gray-100 rounded-md">
          <label className="block font-semibold mb-2">
            2. Hiện tại, anh/chị có bị các bệnh: viêm khớp, dạ dày, viêm
            gan/vàng da, bệnh tim, huyết áp thấp/cao, hen, ho kéo dài, bệnh máu,
            lao?
          </label>
          <div className="flex items-center mb-2">
            <input type="radio" id="q2_yes" name="q2" className="mr-2" />
            <label htmlFor="q2_yes">Có</label>
          </div>
          <div className="flex items-center mb-2">
            <input type="radio" id="q2_no" name="q2" className="mr-2" />
            <label htmlFor="q2_no">Không</label>
          </div>
          <div className="flex items-center">
            <input type="radio" id="q2_other" name="q2" className="mr-2" />
            <label htmlFor="q2_other">Bệnh khác</label>
          </div>
          <textarea
            id="q2_other_text"
            className="resize-none mt-2 w-full p-2 border border-gray-300 rounded"
            placeholder="Vui lòng nhập bệnh khác (nếu có)"
            defaultValue={""}
          />
        </div>
        {/* Question 3 */}
        <div className="mb-4 p-4 bg-gray-100 rounded-md">
          <label className="block font-semibold mb-2">
            3. Trong vòng 12 tháng gần đây, anh/chị có mắc các bệnh và đã được
            điều trị khỏi
          </label>
          <div className="flex items-center mb-2">
            <input
              type="checkbox"
              id="q3_diseases"
              name="q3[]"
              className="mr-2"
            />
            <label htmlFor="q3_diseases">
              Sốt rét, Giang mai, Lao, Viêm não, Phẫu thuật ngoại khoa?
            </label>
          </div>
          <div className="flex items-center mb-2">
            <input
              type="checkbox"
              id="q3_transfusion"
              name="q3[]"
              className="mr-2"
            />
            <label htmlFor="q3_transfusion">
              Được truyền máu và các chế phẩm máu?
            </label>
          </div>
          <div className="flex items-center mb-2">
            <input
              type="checkbox"
              id="q3_vaccine"
              name="q3[]"
              className="mr-2"
            />
            <label htmlFor="q3_vaccine">Tiêm Vaxcin bệnh dại</label>
          </div>
          <div className="flex items-center mb-2">
            <input type="checkbox" id="q3_no" name="q3[]" className="mr-2" />
            <label htmlFor="q3_no">Không</label>
          </div>
          <div className="flex items-center">
            <input type="checkbox" id="q3_other" name="q3[]" className="mr-2" />
            <label htmlFor="q3_no">Khác (cụ thể)</label>
          </div>
          <textarea
            id="q2_other_text"
            className="resize-none mt-2 w-full p-2 border border-gray-300 rounded"
            placeholder="Vui lòng nhập bệnh khác (nếu có)"
            defaultValue={""}
          />
        </div>
        {/* Question 4 */}
        <div className="mb-4 p-4 bg-gray-100 rounded-md">
          <label className="block font-semibold mb-2">
            4. Trong vòng 06 tháng gần đây, anh/chị có bị một trong số các triệu
            chứng sau không?
          </label>
          <div className="flex items-center mb-2">
            <input
              type="checkbox"
              id="q3_diseases"
              name="q3[]"
              className="mr-2"
            />
            <label htmlFor="q3_diseases">
              Sút cân nhanh không rõ nguyên nhân?
            </label>
          </div>
          <div className="flex items-center mb-2">
            <input
              type="checkbox"
              id="q3_transfusion"
              name="q3[]"
              className="mr-2"
            />
            <label htmlFor="q3_transfusion">Nổi hạch kéo dài?</label>
          </div>
          <div className="flex items-center mb-2">
            <input
              type="checkbox"
              id="q3_vaccine"
              name="q3[]"
              className="mr-2"
            />
            <label htmlFor="q3_vaccine">Chữa răng, châm cứu?</label>
          </div>
          <div className="flex items-center mb-2">
            <input type="checkbox" id="q3_no" name="q3[]" className="mr-2" />
            <label htmlFor="q3_no">Xăm mình, xỏ lỗ tai, lỗ mũi.</label>
          </div>
          <div className="flex items-center mb-2">
            <input type="checkbox" id="q3_no" name="q3[]" className="mr-2" />
            <label htmlFor="q3_no">Sử dụng ma túy?</label>
          </div>
          <div className="flex items-center mb-2">
            <input type="checkbox" id="q3_no" name="q3[]" className="mr-2" />
            <label htmlFor="q3_no">
              Quan hệ tình dục với người nhiễm HIV hoặc người có hành vi nguy cơ
              lây nhiễm HIV
            </label>
          </div>
          <div className="flex items-center mb-2">
            <input type="checkbox" id="q3_no" name="q3[]" className="mr-2" />
            <label htmlFor="q3_no">Quan hệ tình dục với người cùng giới?</label>
          </div>
          <div className="flex items-center mb-2">
            <input type="checkbox" id="q3_no" name="q3[]" className="mr-2" />
            <label htmlFor="q3_no">Không</label>
          </div>
          <div className="flex items-center">
            <input type="checkbox" id="q3_other" name="q3[]" className="mr-2" />
            <label htmlFor="q3_no">Mục khác</label>
          </div>
          <textarea
            id="q2_other_text"
            className="resize-none mt-2 w-full p-2 border border-gray-300 rounded"
            placeholder="Vui lòng nhập bệnh khác (nếu có)"
            defaultValue={""}
          />
        </div>
        {/* Question 5 */}
        <div className="mb-4 p-4 bg-gray-100 rounded-md">
          <label className="block font-semibold mb-2">
            5. Trong 01 tháng gần đây anh/chị có
          </label>
          <div className="flex items-center mb-2">
            <input
              type="checkbox"
              id="q3_diseases"
              name="q3[]"
              className="mr-2"
            />
            <label htmlFor="q3_diseases">
              Khỏi bệnh sau khi mắc bệnh viêm đường tiết niệu, viêm da nhiễm
              trùng, viêm phế quản, viêm phổi, sởi, quai bị, Rubella, Khác
            </label>
          </div>
          <div className="flex items-center mb-2">
            <input
              type="checkbox"
              id="q3_transfusion"
              name="q3[]"
              className="mr-2"
            />
            <label htmlFor="q3_transfusion">Tiêm vắc xin phòng bệnh?</label>
          </div>
          <div className="flex items-center mb-2">
            <input
              type="checkbox"
              id="q3_vaccine"
              name="q3[]"
              className="mr-2"
            />
            <label htmlFor="q3_vaccine">
              Đi vào vùng có dịch bệnh lưu hành (sốt rét, sốt xuất huyết,
              Zika,...)
            </label>
          </div>
          <div className="flex items-center mb-2">
            <input type="checkbox" id="q3_no" name="q3[]" className="mr-2" />
            <label htmlFor="q3_no">Không</label>
          </div>
          <div className="flex items-center">
            <input type="checkbox" id="q3_other" name="q3[]" className="mr-2" />
            <label htmlFor="q3_no">Mục khác</label>
          </div>
          <textarea
            id="q2_other_text"
            className="resize-none mt-2 w-full p-2 border border-gray-300 rounded"
            placeholder="Vui lòng nhập bệnh khác (nếu có)"
            defaultValue={""}
          />
        </div>
        {/* Question 6 */}
        <div className="mb-4 p-4 bg-gray-100 rounded-md">
          <label className="block font-semibold mb-2">
            6. Trong 07 ngày gần đây anh/chị có
          </label>
          <div className="flex items-center mb-2">
            <input
              type="checkbox"
              id="q3_diseases"
              name="q3[]"
              className="mr-2"
            />
            <label htmlFor="q3_diseases">
              Bị cảm cúm (ho, nhức đầu, sốt...)?
            </label>
          </div>
          <div className="flex items-center mb-2">
            <input
              type="checkbox"
              id="q3_transfusion"
              name="q3[]"
              className="mr-2"
            />
            <label htmlFor="q3_transfusion">
              Dùng thuốc kháng sinh, Aspirin, Corticoid?
            </label>
          </div>
          <div className="flex items-center mb-2">
            <input
              type="checkbox"
              id="q3_vaccine"
              name="q3[]"
              className="mr-2"
            />
            <label htmlFor="q3_vaccine">
              Tiêm Vaxcin phòng Viêm gan siêu vi B, Human Papilloma Virus.
            </label>
          </div>
          <div className="flex items-center mb-2">
            <input type="checkbox" id="q3_no" name="q3[]" className="mr-2" />
            <label htmlFor="q3_no">Không</label>
          </div>
          <div className="flex items-center">
            <input type="checkbox" id="q3_other" name="q3[]" className="mr-2" />
            <label htmlFor="q3_no">Mục khác</label>
          </div>
          <textarea
            id="q2_other_text"
            className="resize-none mt-2 w-full p-2 border border-gray-300 rounded"
            placeholder="Vui lòng nhập bệnh khác (nếu có)"
            defaultValue={""}
          />
        </div>
        {/* Question 7 */}
        <div className="mb-4 p-4 bg-gray-100 rounded-md">
          <label className="block font-semibold mb-2">
            7. Câu hỏi dành cho phụ nữ
          </label>
          <div className="flex items-center mb-2">
            <input
              type="checkbox"
              id="q3_diseases"
              name="q3[]"
              className="mr-2"
            />
            <label htmlFor="q3_diseases">
              Hiện có thai hoặc nuôi con dưới 12 tháng tuổi?
            </label>
          </div>
          <div className="flex items-center mb-2">
            <input
              type="checkbox"
              id="q3_transfusion"
              name="q3[]"
              className="mr-2"
            />
            <label htmlFor="q3_transfusion">
              Có kinh nguyệt trong vòng một tuần hay không
            </label>
          </div>
          <div className="flex items-center mb-2">
            <input type="checkbox" id="q3_no" name="q3[]" className="mr-2" />
            <label htmlFor="q3_no">Không</label>
          </div>
        </div>
        {/* Question 8 */}
        <div className="mb-4 p-4 bg-gray-100 rounded-md">
          <label className="block font-semibold mb-2">
            8. Anh/chị có đồng ý xét nghiệm HIV, nhận thông báo và được tư vấn
            khi kết quả xét nghiệm HIV nghi ngờ hoặc dương tính?
          </label>
          <div className="flex items-center mb-2">
            <input
              type="checkbox"
              id="q3_diseases"
              name="q3[]"
              className="mr-2"
            />
            <label htmlFor="q3_diseases">Có</label>
          </div>
        </div>
        <div className="flex justify-between mt-6">
        
          <button
            type="button"
            className="bg-gray-200 text-black px-6 py-2 rounded"
            onClick={() => onTabChange("TIME_ADDRESS")}
          >
            Quay về
          </button>
          <Link to="/Appointments">
          <button
            type="submit"
            className="bg-blue-500 text-white px-6 py-2 rounded"
          >
            Tiếp tục
          </button>
        </Link>
          
        </div>
      </form>
    </div>
  );
};

export default FormTab;
