import React, { useState } from 'react';
import { motion, AnimatePresence } from 'framer-motion';
import {Link} from "react-router-dom";

// Color theme references (not necessary but useful for clarity)
// Primary: #4335A7
// Secondary: #80C4E9
// Background: #FFF6E9
// Accent: #FF7F3E

// You can customize button links, icons, etc. as per your app's needs.

const NavBar: React.FC = () => {
    const [menuOpen, setMenuOpen] = useState(false);

    return (
        <nav className="bg-[#FFF6E9] border-b border-[#80C4E9]">
            <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
                <div className="flex justify-between h-16 items-center">
                    {/* Left: Logo */}
                    <div className="flex-shrink-0 flex items-center">
                        <Link to="/" className="text-2xl font-bold text-[#4335A7] tracking-wide">
                            Javinity
                        </Link>
                    </div>

                    {/* Desktop Search and Write Button */}
                    <div className="hidden md:flex md:items-center md:space-x-4">
                        <div className="relative">
                            <input
                                type="text"
                                placeholder="Search..."
                                className="px-3 py-1.5 rounded-md border border-[#80C4E9] focus:outline-none focus:border-[#4335A7] transition-colors duration-200"
                            />
                            <span className="absolute right-2 top-1/2 transform -translate-y-1/2 text-gray-500">
                <svg
                    xmlns="http://www.w3.org/2000/svg"
                    className="h-4 w-4"
                    fill="none"
                    viewBox="0 0 24 24"
                    stroke="currentColor"
                    strokeWidth={2}
                >
                  <path
                      strokeLinecap="round"
                      strokeLinejoin="round"
                      d="M10 6a4 4 0 104 4 4 4 0 00-4-4zM21 21l-4.35-4.35"
                  />
                </svg>
              </span>
                        </div>
                        <Link
                            to="/auth"
                            className="px-4 py-2 rounded-md bg-[#FF7F3E] text-white font-medium hover:bg-[#ff914f] transition-colors duration-200"
                        >
                            Write
                        </Link>
                    </div>

                    {/* Mobile Menu Button */}
                    <div className="md:hidden">
                        <button
                            onClick={() => setMenuOpen(!menuOpen)}
                            className="text-gray-700 focus:outline-none"
                            aria-label="Toggle Menu"
                        >
                            {menuOpen ? (
                                // Close Icon
                                <svg
                                    className="h-6 w-6"
                                    xmlns="http://www.w3.org/2000/svg"
                                    fill="none" viewBox="0 0 24 24" stroke="currentColor" strokeWidth={2}>
                                    <path strokeLinecap="round" strokeLinejoin="round" d="M6 18L18 6M6 6l12 12" />
                                </svg>
                            ) : (
                                // Hamburger Icon
                                <svg
                                    className="h-6 w-6"
                                    xmlns="http://www.w3.org/2000/svg"
                                    fill="none" viewBox="0 0 24 24" stroke="currentColor" strokeWidth={2}>
                                    <path strokeLinecap="round" strokeLinejoin="round" d="M4 8h16M4 16h16" />
                                </svg>
                            )}
                        </button>
                    </div>
                </div>
            </div>

            {/* Mobile Menu (Animated) */}
            <AnimatePresence>
                {menuOpen && (
                    <motion.div
                        initial={{ height: 0, opacity: 0 }}
                        animate={{ height: 'auto', opacity: 1 }}
                        exit={{ height: 0, opacity: 0 }}
                        className="md:hidden overflow-hidden"
                    >
                        <div className="px-4 pb-4 flex flex-col space-y-3 bg-[#FFF6E9] border-t border-[#80C4E9]">
                            {/* Search Bar for Mobile */}
                            <div className="relative">
                                <input
                                    type="text"
                                    placeholder="Search..."
                                    className="w-full px-3 py-2 rounded-md border border-[#80C4E9] focus:outline-none focus:border-[#4335A7] transition-colors duration-200"
                                />
                                <span className="absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-500">
                  <svg
                      xmlns="http://www.w3.org/2000/svg"
                      className="h-4 w-4"
                      fill="none"
                      viewBox="0 0 24 24"
                      stroke="currentColor"
                      strokeWidth={2}
                  >
                    <path
                        strokeLinecap="round"
                        strokeLinejoin="round"
                        d="M10 6a4 4 0 104 4 4 4 0 00-4-4zM21 21l-4.35-4.35"
                    />
                  </svg>
                </span>
                            </div>

                            {/* Write Button for Mobile */}
                            <Link
                                to="/write"
                                className="block text-center px-4 py-2 rounded-md bg-[#FF7F3E] text-white font-medium hover:bg-[#ff914f] transition-colors duration-200"
                            >
                                Write
                            </Link>
                        </div>
                    </motion.div>
                )}
            </AnimatePresence>
        </nav>
    );
};

export default NavBar;