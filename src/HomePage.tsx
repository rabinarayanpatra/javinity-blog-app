import React from 'react';
import { motion } from 'framer-motion';
import {Link} from "react-router-dom";

// Color theme (for reference)
// Primary: #4335A7
// Secondary: #80C4E9
// Background: #FFF6E9
// Accent: #FF7F3E

const HomePage: React.FC = () => {
    return (
        <div className="bg-[#FFF6E9] min-h-screen text-gray-800">
            {/* Hero Section */}
            <HeroSection />

            {/* Featured Posts Section */}
            <FeaturedPostsSection />

            {/* About Section */}
            <AboutSection />

            {/* Newsletter Call-to-Action */}
            <NewsletterSection />
        </div>
    );
};

export default HomePage;

// ----------------- Subcomponents -----------------

const HeroSection: React.FC = () => {
    return (
        <section className="relative overflow-hidden">
            <div className="max-w-7xl mx-auto px-6 py-16 flex flex-col md:flex-row items-center">
                <motion.div
                    initial={{ opacity: 0, x: -20 }}
                    whileInView={{ opacity: 1, x:0 }}
                    transition={{ duration: 0.6 }}
                    viewport={{ once: true }}
                    className="flex-1 text-center md:text-left"
                >
                    <h1 className="text-4xl md:text-5xl font-bold text-[#4335A7] mb-4">
                        Welcome to Javinity
                    </h1>
                    <p className="text-lg md:text-xl text-gray-600 mb-6">
                        Discover stories, insights, and knowledge from around the world.
                    </p>
                    <Link
                        to="/auth"
                        className="inline-block px-6 py-3 bg-[#FF7F3E] text-white rounded-md hover:bg-[#ff914f] transition-colors duration-200 font-medium"
                    >
                        Start Writing
                    </Link>
                </motion.div>
                <motion.div
                    initial={{ opacity: 0, x: 20 }}
                    whileInView={{ opacity: 1, x:0 }}
                    transition={{ duration: 0.6 }}
                    viewport={{ once: true }}
                    className="flex-1 mt-10 md:mt-0 flex justify-center md:justify-end"
                >
                    {/* Placeholder image / illustration */}
                    <img
                        src="https://via.placeholder.com/400x300?text=Javinity+Hero+Image"
                        alt="Hero Illustration"
                        className="max-w-full h-auto rounded-lg shadow-lg"
                    />
                </motion.div>
            </div>
        </section>
    );
};

const FeaturedPostsSection: React.FC = () => {
    const posts = [
        {
            title: 'Exploring the Latest in JavaScript',
            image: 'https://via.placeholder.com/400x250?text=Post+1',
            description: 'A brief overview of modern JS frameworks.'
        },
        {
            title: 'Understanding CSS-in-JS',
            image: 'https://via.placeholder.com/400x250?text=Post+2',
            description: 'Styling techniques that will change how you build UIs.'
        },
        {
            title: 'Building a Career in Tech',
            image: 'https://via.placeholder.com/400x250?text=Post+3',
            description: 'Tips and advice for aspiring developers.'
        }
    ];

    return (
        <section className="py-16">
            <div className="max-w-7xl mx-auto px-6">
                <motion.h2
                    initial={{ opacity: 0, y:20 }}
                    whileInView={{ opacity:1, y:0 }}
                    transition={{ duration:0.5 }}
                    viewport={{ once:true }}
                    className="text-3xl font-bold text-[#4335A7] mb-8 text-center"
                >
                    Featured Posts
                </motion.h2>
                <div className="grid gap-8 md:grid-cols-3">
                    {posts.map((post, idx) => (
                        <PostCard key={idx} post={post} />
                    ))}
                </div>
            </div>
        </section>
    );
};

interface Post {
    title: string;
    image: string;
    description: string;
}

const PostCard: React.FC<{ post: Post }> = ({ post }) => {
    return (
        <motion.div
            initial={{ opacity:0, y:20 }}
            whileInView={{ opacity:1, y:0 }}
            transition={{ duration:0.5, delay:0.1 }}
            viewport={{ once:true }}
            className="bg-white rounded-lg overflow-hidden shadow hover:shadow-lg transition-shadow duration-300"
        >
            <div className="overflow-hidden">
                <img src={post.image} alt={post.title} className="w-full h-auto object-cover transition-transform duration-300 hover:scale-105" />
            </div>
            <div className="p-4">
                <h3 className="text-xl font-semibold text-[#4335A7] mb-2">{post.title}</h3>
                <p className="text-gray-600 mb-4">{post.description}</p>
                <Link
                    to="/blog"
                    className="text-[#FF7F3E] font-medium hover:underline"
                >
                    Read More →
                </Link>
            </div>
        </motion.div>
    );
};

const AboutSection: React.FC = () => {
    return (
        <section className="py-16 bg-[#80C4E9]/10">
            <div className="max-w-4xl mx-auto px-6 text-center">
                <motion.h2
                    initial={{ opacity:0, y:20 }}
                    whileInView={{ opacity:1, y:0 }}
                    transition={{ duration:0.5 }}
                    viewport={{ once:true }}
                    className="text-3xl font-bold text-[#4335A7] mb-6"
                >
                    About Javinity
                </motion.h2>
                <motion.p
                    initial={{ opacity:0, y:20 }}
                    whileInView={{ opacity:1, y:0 }}
                    transition={{ duration:0.5, delay:0.1 }}
                    viewport={{ once:true }}
                    className="text-lg text-gray-700 leading-relaxed"
                >
                    Javinity is a platform where passionate writers, readers, and learners converge.
                    Our mission is to create a welcoming environment for knowledge exchange,
                    storytelling, and inspiration. Whether you are a seasoned writer or just
                    starting out, Javinity offers a space to share your voice and connect with
                    a vibrant community.
                </motion.p>
            </div>
        </section>
    );
};

const NewsletterSection: React.FC = () => {
    return (
        <section className="py-16">
            <div className="max-w-md mx-auto px-6 text-center">
                <motion.h2
                    initial={{ opacity:0, y:20 }}
                    whileInView={{ opacity:1, y:0 }}
                    transition={{ duration:0.5 }}
                    viewport={{ once:true }}
                    className="text-3xl font-bold text-[#4335A7] mb-4"
                >
                    Stay Updated
                </motion.h2>
                <motion.p
                    initial={{ opacity:0, y:20 }}
                    whileInView={{ opacity:1, y:0 }}
                    transition={{ duration:0.5, delay:0.1 }}
                    viewport={{ once:true }}
                    className="text-gray-700 mb-6"
                >
                    Subscribe to our newsletter and never miss the latest posts, insights,
                    and tips from Javinity.
                </motion.p>
                <motion.div
                    initial={{ opacity:0, y:20 }}
                    whileInView={{ opacity:1, y:0 }}
                    transition={{ duration:0.5, delay:0.2 }}
                    viewport={{ once:true }}
                    className="flex flex-col sm:flex-row items-center justify-center"
                >
                    <input
                        type="email"
                        className="w-full sm:w-auto sm:flex-1 px-4 py-2 border border-[#80C4E9] rounded-md focus:outline-none focus:border-[#4335A7] transition duration-200 mb-3 sm:mb-0 sm:mr-2"
                        placeholder="Enter your email"
                    />
                    <button
                        className="px-5 py-2 bg-[#FF7F3E] text-white rounded-md hover:bg-[#ff914f] transition duration-200"
                    >
                        Subscribe
                    </button>
                </motion.div>
            </div>
        </section>
    );
};