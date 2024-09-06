import { AutoAwesome } from "@mui/icons-material";
import { Box } from "@mui/material";
import Markdown from "react-markdown";
import rehypeRaw from "rehype-raw";
export const Blog = ({ markdown }) => {

  return (
    <Box className="aiResponse">
      <Box className="aiIcon">
        <AutoAwesome />
      </Box>
      <div className="emailMarkdown">
        <Markdown rehypePlugins={[rehypeRaw]}>{markdown}</Markdown>
      </div>
    </Box>
  );
};
