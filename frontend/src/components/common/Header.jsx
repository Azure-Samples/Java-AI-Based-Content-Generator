import IconButton from "@mui/material/IconButton";
import Menu from "@mui/material/Menu";
import MenuItem from "@mui/material/MenuItem";
import { useEffect, useState } from "react";
import Container from "@mui/material/Container";
import { msalInstance } from "../../index";
import { Box, Button } from "@mui/material";
import { useDispatch } from "react-redux";
import { setUserName } from "../../store/userSlice";
import { Link } from "react-router-dom";

export const Header = () => {
  const dispatch = useDispatch();
  const [anchorEl, setAnchorEl] = useState(null);
  const open = Boolean(anchorEl);
  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };

  const [accountInfo, setAccountInfo] = useState(null);

  useEffect(() => {
    setAccountInfo(msalInstance.getActiveAccount());
  }, []);

  const getPersonaString = (val) => {
    if (!!!val || val === "") {
      return "UU";
    }
    let tempArr = val.split(" ");
    if (tempArr.length >= 2) {
      return tempArr[0][0].toUpperCase() + tempArr[1][0].toUpperCase();
    } else {
      return tempArr[0][0].toUpperCase();
    }
  };

  // redux - storing user data
  dispatch(setUserName(getPersonaString(accountInfo?.name)));

  return (
      <div className="headerMain">
        <Container fixed>
          <h5>
            <Link to="/">App Modernization: Content Generation Sample</Link>
          </h5>
          <Box display={"flex"} alignItems={"center"}>
            <Box className="menuItems">
              <Link to="/about">About</Link>
            </Box>
            <IconButton
                aria-label="more"
                id="long-button"
                aria-controls={open ? "long-menu" : undefined}
                aria-expanded={open ? "true" : undefined}
                aria-haspopup="true"
                onClick={handleClick}
                className="btnHeader"
            >
              {getPersonaString(accountInfo?.name)}
            </IconButton>
            <Menu
                id="long-menu"
                MenuListProps={{
                  "aria-labelledby": "long-button"
                }}
                anchorEl={anchorEl}
                open={open}
                onClose={handleClose}
                anchorOrigin={{
                  vertical: "bottom",
                  horizontal: "right"
                }}
                transformOrigin={{
                  vertical: "top",
                  horizontal: "right"
                }}
            >
              <MenuItem onClick={handleClose}>{accountInfo?.name}</MenuItem>
              <MenuItem onClick={handleClose}>{accountInfo?.username}</MenuItem>
              <MenuItem onClick={handleClose}>
                <Button
                    onClick={() => {
                      msalInstance.clearCache();
                      window.location.href = window.location.origin;
                    }}
                >
                  Logout
                </Button>
              </MenuItem>
            </Menu>
          </Box>
        </Container>
      </div>
  );
};
