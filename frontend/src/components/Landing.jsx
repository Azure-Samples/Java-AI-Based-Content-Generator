import { Box, Container } from "@mui/material";
import {Fragment, useEffect} from "react";
import { useSelector } from "react-redux";
import { Email } from "./Email";
import { SocialMedia } from "./SocialMedia";
import { Blog } from "./Blog";
import parse from "html-react-parser";
import { Footer } from "./common/Footer";

export const Landing = () => {
  const promptData = useSelector((state) => state.prompt.userSearch);
  const promptApiError = useSelector((state) => state.prompt.error);
  const userName = useSelector((state) => state.user.userData);

  return (
      <Fragment>
        {promptData.length === 0 ? (
            <Box className="landingPage">
              <Container fixed>
                <h1>
                  {parse(
                      promptApiError
                          ? "<span>No Content</span>"
                          : "Effortless Marketing <br /> Content at Your Fingertips"
                  )}
                </h1>
              </Container>
            </Box>
        ) : (
            promptData.map((item, index) => (
                <Container fixed className="responseChat" key={index.toString()}>
                  <Box className="userText">
                    <Box className="userIcon">{userName}</Box>
                    <p>{item.userInput}</p>
                  </Box>
                  {item.promptType === "mail_template" ? (
                      <Email markdown={item.markdownData} />
                  ) : item.promptType === "social_media" ? (
                      <SocialMedia markdown={item.markdownData} />
                  ) : (
                      <Blog markdown={item.markdownData} />
                  )}
                </Container>
            ))
        )}
        <Footer />
      </Fragment>
  );
};
