import classNames from "classnames";
import React, { useMemo, useState } from "react";

const ContentSection = ({ question, answers }) => {
  const [visible, setVisible] = useState(false);

  const toggleVisible = () => setVisible(!visible);

  return (
    <>
      <button
        onClick={toggleVisible}
        className="w-full text-left p-4 border-2 rounded-lg border-solid border-[rgb(187,215,253)] accordion-button"
      >
        <span className="text-blue-600 font-semibold">{question}</span>
        <span className="float-right">{visible ? "▲" : "▼"}</span>
      </button>

      <div
        className={classNames(
          "p-4 bg-gray-100 border rounded-[20px_20px_20px_20px]",
          {
            "hidden-content": !visible,
          }
        )}
      >
        {answers.map((it, index) => (
          <p key={index}>{it}</p>
        ))}
      </div>
    </>
  );
};

const Faq = () => {
  const data = useMemo(() => {
    return [
      {
        question: "Ai có thể tham gia hiến máu?",
        answers: [
          "- Tất cả mọi người từ 18 - 60 tuổi, thực sự tình nguyện hiến máu của mình để cứu chữa người bệnh.",
          "- Cân nặng ít nhất là 45kg đối với phụ nữ, nam giới. Lượng máu hiến mỗi lần không quá 9ml/kg cân nặng và không quá 500ml mỗi lần.",
          "- Không bị nhiễm hoặc không có các hành vi lây nhiễm HIV và các bệnh lây nhiễm qua đường truyền máu khác.",
          "- Thời gian giữa 2 lần hiến máu là 12 tuần đối với cả Nam và Nữ.",
          "- Có giấy tờ tùy thân.",
        ],
      },
      {
        question: "Ai là người không nên hiến máu",
        answers: [
          "- Người đã nhiễm hoặc đã thực hiện hành vi có nguy cơ nhiễm HIV, viêm gan B, viêm gan C, và các vius lây qua đường truyền máu.",
          "- Người có các bệnh mãn tính: tim mạch, huyết áp, hô hấp, dạ dày…",
        ],
      },
      {
        question: "Máu của tôi sẽ được làm những xét nghiệm gì?",
        answers: [
          "- Tất cả những đơn vị máu thu được sẽ được kiểm tra nhóm máu (hệ ABO, hệ Rh), HIV, virus viêm gan B, virus viêm gan C, giang mai, sốt rét.",
          "- Bạn sẽ được thông báo kết quả, được giữ kín và được tư vấn (miễn phí) khi phát hiện ra các bệnh nhiễm trùng nói trên.",
        ],
      },
      {
        question: "Máu gồm những thành phần và chức năng gì?",
        answers: [
          "Máu là một chất lỏng lưu thông trong các mạch máu của cơ thể, gồm nhiều thành phần, mỗi thành phần làm nhiệm vụ khác nhau:",
          "- Hồng cầu làm nhiệm vụ chính là vận chuyển oxy.",
          "- Bạch cầu làm nhiệm vụ bảo vệ cơ thể.",
          "- Tiểu cầu tham gia vào quá trình đông cầm máu.",
          "- Huyết tương: gồm nhiều thành phần khác nhau: kháng thể, các yếu tố đông máu, các chất dinh dưỡng...",
        ],
      },
      {
        question: "Tại sao lại có nhiều người cần phải được truyền máu?",
        answers: [
          "Mỗi giờ có hàng trăm người bệnh cần phải được truyền máu vì :",
          "- Bị mất máu do chấn thương, tai nạn, thảm hoạ, xuất huyết tiêu hoá...",
          "- Do bị các bệnh gây thiếu máu, chảy máu: ung thư máu, suy tuỷ xương, máu khó đông...",
          "- Các phương pháp điều trị hiện đại cần truyền nhiều máu: phẫu thuật tim mạch, ghép tạng...",
        ],
      },
      {
        question: "Nhu cầu máu điều trị ở nước ta hiện nay?",
        answers: [
          "- Mỗi năm nước ta cần khoảng 1.800.000 đơn vị máu điều trị.",
          "- Máu cần cho điều trị hằng ngày, cho cấp cứu, cho dự phòng các thảm họa, tai nạn cần truyền máu với số lượng lớn.",
          "- Hiện tại chúng ta đã đáp ứng được khoảng 54% nhu cầu máu cho điều trị.",
        ],
      },
      {
        question: "Tại sao khi tham gia hiến máu lại cần phải có giấy CMND?",
        answers: [
          "Mỗi đơn vị máu đều phải có hồ sơ, trong đó có các thông tin về người hiến máu. Theo quy định, đây là một thủ tục cần thiết trong quy trình hiến máu để đảm bảo tính xác thực thông tin về người hiến máu.",
        ],
      },
    ];
  }, []);

  return (
    <div className="max-w-2xl my-6 mx-auto p-4">
      <h2 className="text-3xl font-semibold mb-4 text-blue-800 text-center">
        Lưu ý quan trọng
      </h2>
      <div className="space-y-2">
        {data.map((it, index) => (
          <ContentSection
            key={index}
            question={`${index + 1}. ${it.question}`}
            answers={it.answers}
          />
        ))}
      </div>
    </div>
  );
};

export default Faq;
