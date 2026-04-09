"use client";

import { useState} from "react";
import { useRouter} from "next/navigation";

export default function Regular_Sign_Up() {
    const router = useRouter();

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [inviteCode, setInviteCode] = useState("");

    const handleEmailChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setEmail(e.target.value);
    };
    
    const handlePasswordChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setPassword(e.target.value);
    };  
    const handleConfirmPasswordChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setConfirmPassword(e.target.value);
    };
    const handleInviteCodeChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setInviteCode(e.target.value);
    };

    const handleSubmit = (e: React.SubmitEvent<HTMLFormElement>) => {
        e.preventDefault();
        console.log("Form submitted");
        router.push("/");
        // Handle form submission logic here
    }

    return (
        <div className="flex flex-col items-center justify-center h-screen dark">
            <div className="w-full bg-gray-800 rounded-lg shadow-md p-6">
                <h2 className="text-2xl font-bold text-gray-200 mb-4">Sign Up</h2>
                <form className="flex flex-col w-full" onSubmit={handleSubmit}>
                    <input 
                        placeholder="Email address" 
                        className="bg-gray-700 text-gray-200 border-0 rounded-md p-2 mb-4 focus:bg-gray-600 focus:outline-none focus:ring-1 focus:ring-blue-500 transition ease-in-out duration-150" 
                        type="email" 
                        value={email}
                        onChange={handleEmailChange}
                        required
                    />
                    <input 
                        placeholder="Password" 
                        className="bg-gray-700 text-gray-200 border-0 rounded-md p-2 mb-4 focus:bg-gray-600 focus:outline-none focus:ring-1 focus:ring-blue-500 transition ease-in-out duration-150" 
                        type="password" 
                        value={password}
                        onChange={handlePasswordChange}
                        required
                    />
                    <input 
                        placeholder="Confirm Password" 
                        className="bg-gray-700 text-gray-200 border-0 rounded-md p-2 mb-4 focus:bg-gray-600 focus:outline-none focus:ring-1 focus:ring-blue-500 transition ease-in-out duration-150" 
                        value={confirmPassword}
                        onChange={handleConfirmPasswordChange}
                        type="password" 
                        required
                    />

                    <input 
                        placeholder="Invite Code" 
                        className="bg-gray-700 text-gray-200 border-0 rounded-md p-2 mb-4 focus:bg-gray-600 focus:outline-none focus:ring-1 focus:ring-blue-500 transition ease-in-out duration-150" 
                        value={inviteCode}
                        onChange={handleInviteCodeChange}
                        type="text" 
                        required
                    />

                    <div className="flex items-center justify-between flex-wrap">
                        <label className="text-sm text-gray-200 cursor-pointer" htmlFor="remember-me">
                            {/* Add checkbox here if needed */}
                        </label>
                        <a className="text-sm text-blue-500 hover:underline mb-0.5 block" href="#">Forgot password?</a>
                        <p className="text-white mt-4">
                            Already have an account? 
                            <a className="text-sm text-blue-500 hover:underline mt-4" href="/auth/login">
                                Login
                            </a>
                        </p>
                    </div>
                    <button 
                        className="bg-gradient-to-r from-indigo-500 to-blue-500 text-white font-bold py-2 px-4 rounded-md mt-4 hover:bg-indigo-600 hover:to-blue-600 transition ease-in-out duration-150" 
                        type="submit"
                    >
                        Login
                    </button>
                </form>
            </div>
        </div>
    );
}