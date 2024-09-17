import { useEffect, useRef, useState } from "react";
import {
  Box,
  TextField,
  FormControl,
  Select,
  MenuItem,
  IconButton,
  Container,
  Typography,
  Snackbar,
  Alert
} from "@mui/material";
import SearchIcon from "@mui/icons-material/Search";

import { useDispatch, useSelector } from "react-redux";
import {
  getMarkdownData,
  setApiError,
  setUserPrompt
} from "../../store/promptSlice";
import instance from "../../config/axios";

export const Footer = () => {
  // prompt type values
  const promptTypes = [
    {
      value: "mail_template",
      text: "Mail Template"
    },
    {
      value: "social_media",
      text: "Social Media Posts"
    },
    {
      value: "blog",
      text: "Blog Templates"
    }
  ];

  const [selectedValue, setSelectedValue] = useState("mail_template");
  const [inputValue, setInputValue] = useState("");
  const [isMultiline, setIsMultiline] = useState(false);
  const [inputError, setInputError] = useState(false);
  const [loading, setLoading] = useState(false);
  const inputRef = useRef(null);
  const containerRef = useRef(null);
  const dispatch = useDispatch();
  const noProduct = useSelector((state) => state.prompt.errorProductNotFound);
  const [errorNoProduct, setErrorNoProduct] = useState(false);
  // changing UI once the prompt reaches second row
  useEffect(() => {
    const lineHeight = parseInt(
        window.getComputedStyle(inputRef.current).lineHeight,
        10
    );
    const scrollHeight = inputRef.current.scrollHeight;
    const rows = Math.floor(scrollHeight / lineHeight);

    if (inputValue.trim() === "") {
      setIsMultiline(false);
    } else if (rows > 1) {
      setIsMultiline(true);
    }
  }, [inputValue]);

  // prompt type change function
  const handleDropdownChange = (event) => {
    setSelectedValue(event.target.value);
  };

  // Input value change function
  const handleInputChange = (event) => {
    setInputValue(event.target.value);
  };

  // search prompt button click handler
  const handleSearchClick = () => {
    if(loading) {
      return
    }
    if (inputValue.trim() === "") {
      setInputError(true);
    } else {
      setInputError(false);
      setLoading(true);

      // fetching values from API
      instance
          .post(process.env.REACT_APP_CONTENT_GENERATOR_ENDPOINT, {
            message: inputValue,
            type: selectedValue
          })
          .then((res) => {
            setLoading(false);
            if (res?.status === 204 || noProduct) {
              setErrorNoProduct(true);
            } else {
              dispatch(getMarkdownData(res.data?.choices[0]?.message?.content));
              dispatch(
                  setUserPrompt({
                    promptType: selectedValue,
                    userInput: inputValue,
                    markdownData: res.data?.choices[0]?.message?.content
                  })
              );
            }
          })
          .catch((err) => {
            setLoading(false);
            dispatch(setApiError(true));
          });
    }
  };

  const handleNoProductClose = () => {
    setErrorNoProduct(false);
  };

  return (
      <footer>
        {/* product not found error snackbar */}
        <Snackbar
            open={errorNoProduct}
            autoHideDuration={6000}
            onClose={handleNoProductClose}
        >
          <Alert
              onClose={handleNoProductClose}
              severity="error"
              variant="filled"
              sx={{ width: "100%" }}
          >
            Product not found
          </Alert>
        </Snackbar>
        <Container
            ref={containerRef}
            fixed
            className={isMultiline ? "chatContainer" : ""}
        >
          {loading && (
              <Typography>
                Please wait we are loading content <span className="loader" />
              </Typography>
          )}

          {/* prompt input */}
          <Box className="chatBox">
            <TextField
                variant="outlined"
                fullWidth={isMultiline}
                multiline
                maxRows={4}
                value={inputValue}
                onChange={handleInputChange}
                placeholder="Enter your prompt here"
                inputRef={inputRef}
                sx={{ flex: "1 1 auto" }}
                className="chatInput"
            />

            {/* prompt types */}
            <Box className="chatDropdown">
              <p>Content Type:</p>
              <FormControl
                  variant="outlined"
                  sx={{ minWidth: 120, flexShrink: 0 }}
              >
                <Select
                    value={selectedValue}
                    onChange={handleDropdownChange}
                    MenuProps={{
                      anchorOrigin: {
                        vertical: "top",
                        horizontal: "center"
                      },
                      transformOrigin: {
                        vertical: "bottom",
                        horizontal: "center"
                      },
                      getContentAnchorEl: null,
                      disableScrollLock: true
                    }}
                >
                  {promptTypes.map((type, index) => (
                      <MenuItem
                          value={type.value}
                          selected={index === 0 && "selected"}
                          key={index.toString()}
                      >
                        {type.text}
                      </MenuItem>
                  ))}
                </Select>
              </FormControl>
            </Box>

            {/* prompt search button */}
            <IconButton
                sx={{ flexShrink: 0 }}
                className="chatIcon"
                onClick={handleSearchClick}
            >
              <SearchIcon />
            </IconButton>
            {inputError && (
                <Typography color="error" className="promptError">
                  Please enter your search in prompt
                </Typography>
            )}
          </Box>
        </Container>
      </footer>
  );
};
