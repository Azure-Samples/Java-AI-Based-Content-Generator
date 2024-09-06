import { AutoAwesome } from "@mui/icons-material";
import { Box } from "@mui/material";
import { useEffect, useState } from "react";
import ReactMarkdown from "react-markdown";
import rehypeRaw from "rehype-raw";
import parse from "html-react-parser";

export const Email = ({ markdown }) => {
  const [markdownData, setMarkdownData] = useState([]);

  // Function to separate content into Markdown and HTML segments
  const separateContent = (apiContent) => {
    const segments = [];
    const regex = /```html([\s\S]*?)```/g; // Regex to find HTML sections
    let lastIndex = 0;
    let match;

    // Loop through and find all HTML sections
    while ((match = regex.exec(apiContent)) !== null) {
      // Push Markdown content before HTML
      if (match.index > lastIndex) {
        segments.push({
          type: "markdown",
          content: apiContent.substring(lastIndex, match.index)
        });
      }

      // Push the HTML content
      segments.push({ type: "html", content: match[1] });

      // Update last index
      lastIndex = regex.lastIndex;
    }

    // Push any remaining Markdown after the last HTML section
    if (lastIndex < apiContent.length) {
      segments.push({
        type: "markdown",
        content: apiContent.substring(lastIndex)
      });
    }

    setMarkdownData(segments);
  };
  useEffect(() => {
    separateContent(markdown);
  }, [markdown]);

  return (
    <Box className="aiResponse">
      <Box className="aiIcon">
        <AutoAwesome />
      </Box>
      <div className="emailMarkdown">
        {markdownData?.map((segment, index) => (
          <div key={index}>
            {segment.type === "markdown" ? (
              <ReactMarkdown rehypePlugins={[rehypeRaw]}>
                {segment.content}
              </ReactMarkdown>
            ) : (
                parse(segment.content)
            )}
          </div>
        ))}
      </div>
    </Box>
  );
};
