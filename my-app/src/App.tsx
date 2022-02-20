import * as React from "react";

import "./App.css";
import { Col, Row } from "antd";
import CropperModal from "./common/CropperModal";
import http, { urlBackend } from "./http_common";

function App() {
  const [images, setImages] = React.useState<Array<string>>([]);

  const handleSelected = async (base64: string) => {
    //console.log("base64", base64);
    const imgName = await http.post<string>("upload", { base64 });
    setImages([...images, imgName.data]);
  };

  const dataImages = images.map((item, key) => {
    return (
      <Col md={4} key={key}>
        <div>
          <img src={urlBackend + "files/" + item} alt="images" width="100%" />
        </div>
      </Col>
    );
  });

  return (
    <>
      <h1>Hello react</h1>
      <div>
        <Row gutter={[8, 8]}>
          {dataImages}
          <Col md={4}>
            <CropperModal onSelected={handleSelected} />
          </Col>
        </Row>
      </div>
    </>
  );
}

export default App;
