import React from "react";

export const ErrorComponent = ({error}) => {
    return <h6>An Error Occurred: {error ? error.errorCode : "unknown error"}</h6>;
}
