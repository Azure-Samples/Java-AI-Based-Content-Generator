import { AutoAwesome } from "@mui/icons-material";
import { Box } from "@mui/material";
import Markdown from "react-markdown";
import rehypeRaw from "rehype-raw";
import image from "../assets/images/socialMedia.png";

export const SocialMedia = ({ markdown }) => {
  return (
    <Box className="aiResponse">
      <Box className="aiIcon">
        <AutoAwesome />
      </Box>
      <Box className="responseAI">
        <p className="resPara">Here is a instagram post from us</p>
        <Box className="responsiveData">
        </Box>
        <Box className="mediaResponse">
          <img src={image} alt="Social Media" />
          <div className="emailMarkdown">
            <Markdown rehypePlugins={[rehypeRaw]}>{markdown}</Markdown>
          </div>
        </Box>
      </Box>
    </Box>
  );
};
